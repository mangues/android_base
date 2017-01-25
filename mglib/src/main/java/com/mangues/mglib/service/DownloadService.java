package com.mangues.mglib.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.SparseBooleanArray;

import com.mangues.mglib.data.db.DatabaseHelper;
import com.mangues.mglib.data.net.NetworkDateSource;
import com.mangues.mglib.threadpool.ThreadPoolExecutor;
import com.mangues.mglib.threadpool.ThreadTask;
import com.mangues.mglib.util.UrlUtil;
import com.mangues.mglib.util.apkupdate.DownloadRecord;
import com.mangues.mglib.util.apkupdate.RealmUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class DownloadService extends Service {

    private static final String DOWNLOAD_DB = "update_apk_db";
    public static final String DOWNLOAD_DIR = "download";
    public static final String DOWNLOAD_RECEIVER_ACTION = "com.linkage.finance.service.DownloadService.ReceiverAction";

    public static final int DOWNLOAD_LEN_MSG = 1;
    public static final int DOWNLOAD_PROGRESS_MSG = 2;
    public static final int DOWNLOAD_FINISH_MSG = 3;
    public static final int DOWNLOAD_PAUSE = 4;

    private SparseBooleanArray pauseMap = new SparseBooleanArray();

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public void pauseContinue(int id, boolean pause){
        pauseMap.put(id,pause);
    }


    public void download(final int id,final int version, final String addressUrl) {
        ThreadTask runnable = new ThreadTask() {
            @Override
            public void onRun() {
                //在binder线程池中执行 ,可以请求网络
                try {
                    // 存入用户名及密码
                    RealmUtil realmUtil = new RealmUtil(getApplicationContext());

                    String fileName = UrlUtil.getFileName(addressUrl);
                    //sdcard未挂上去
                    if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
                        return;
                    }
                    File fileDir = getExternalFilesDir(DOWNLOAD_DIR);
                    File file = new File(fileDir,fileName);
                    boolean isExites = !file.exists();
                    if (!file.exists()) { //若存储的文件不存在
                        File parentFile = file.getParentFile();
                        if (!parentFile.exists()){ //获取父文件目录
                            parentFile.mkdirs();
                        }
                        realmUtil.delete(id,version);
                    }
                    RandomAccessFile bos = new RandomAccessFile(file, "rw");



                    List<DownloadRecord> downloadRecords =  realmUtil.find(id,version);
                    DownloadRecord downloadRecord = null;
                    if (null != downloadRecords && downloadRecords.size()>0){
                        downloadRecord = downloadRecords.get(0);
                    }else{
                        downloadRecord = new DownloadRecord();
                        downloadRecord.setRecordId(id);
                        downloadRecord.setUrlAddress(addressUrl);
                        downloadRecord.setFileName(fileName);
                        downloadRecord.setVersion(version);
                        downloadRecord.setCreateTime(System.currentTimeMillis());
                        downloadRecord.setFilePath(file.getAbsolutePath());
                        downloadRecord.setFinishLen(0);
                        downloadRecord.setTotalLen(1000000000);
                        downloadRecord.setDownloadStatus(DOWNLOAD_PROGRESS_MSG);
                        realmUtil.save(downloadRecord);
                    }





                    //根据id,查询数据库的此文件的下载记录信息
                    URL url = new URL(addressUrl); // 记住下载地址
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();



                    long start = downloadRecord.getFinishLen();
                    long end = downloadRecord.getTotalLen() - 1;
                    if(start > 0 ){ //之前下载过
                        conn.setRequestProperty("Range", "bytes=" + start + "-" + end);		// 设置当前线程下载的范围
                        bos.seek(start);
                    }
                    conn.setConnectTimeout(5000);
                    long totalLen = conn.getContentLength(); // 获取服务端发送过来的文件长度
                    if(start > 0 && end > 0 ){ //之前下载过
                        totalLen = end;
                    }
                    downloadRecord.setTotalLen(totalLen);
                    // 发送totalLen到主线程，通过广播方式
                    Intent receiverIntent = new Intent(DOWNLOAD_RECEIVER_ACTION);
                    receiverIntent.putExtra("download_record", downloadRecord);
                    receiverIntent.putExtra("action_type", DOWNLOAD_LEN_MSG);
                    sendBroadcast(receiverIntent);

                    BufferedInputStream bin = new BufferedInputStream(conn.getInputStream());
                    byte[] buffer = new byte[1024 * 500]; // 每次拷贝500K
                    int len = -1;
                    long totalFinish = start;  //下载开始的长度，默认是0，而有继续时，则是上次的start了。
                    while ((len = bin.read(buffer)) != -1) {
//                        if(app.isExit){ //全部暂停了，退出就全部暂停了，下次进入时，不会自动开启，需要用户点击继续时才能下载
//                            downloadRecord.setDownloadStatus(DOWNLOAD_PAUSE);
//                            afeiDb.save(downloadRecord);
//                            return ;
//                        }
                        if (pauseMap.get(id)){ //若是暂停了,则停掉
                            downloadRecord.setDownloadStatus(DOWNLOAD_PAUSE);
                            realmUtil.update(downloadRecord);
                            receiverIntent.putExtra("download_record", downloadRecord);
                            receiverIntent.putExtra("action_type", DOWNLOAD_PAUSE);
                            sendBroadcast(receiverIntent);
                            return ;
                        }
                        bos.write(buffer, 0, len); // 从服务端读取数据, 写到本地文件
                        totalFinish += len; // 统计所有线程总共完成了多少
                        downloadRecord.setFinishLen(totalFinish);
                        downloadRecord.setDownloadStatus(DOWNLOAD_PROGRESS_MSG);
                        realmUtil.update(downloadRecord);
                        receiverIntent.putExtra("download_record", downloadRecord);
                        receiverIntent.putExtra("action_type", DOWNLOAD_PROGRESS_MSG);
                        sendBroadcast(receiverIntent);
                    }
                    bos.close();
                    bin.close();

                    if (totalFinish == totalLen) { // 如果已完成长度等于服务端文件长度(代表下载完成)
                        //L.e("下载完成, 耗时: "		+ (System.currentTimeMillis() - begin));
                        // 通过广播的方式告诉 ui，已经下载完了
                        downloadRecord.setFinishLen(totalFinish);
                        downloadRecord.setDownloadStatus(DOWNLOAD_FINISH_MSG);
                        realmUtil.update(downloadRecord);
                        receiverIntent.putExtra("download_record", downloadRecord);
                        receiverIntent.putExtra("action_type", DOWNLOAD_FINISH_MSG);
                        sendBroadcast(receiverIntent);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        ThreadPoolExecutor.getInstance().execute(runnable);


    }

    public class MyBinder extends Binder implements IDownload {

        @Override
        public void download(int id,int version, String url) {
            DownloadService.this.download(id,version,url);
        }

        @Override
        public void pauseContinue(int id, boolean pause) {
            DownloadService.this.pauseContinue(id,pause);
        }
    }



    public interface IDownload {
        /**
         * id代表下载apk的任务标识
         * @param id
         * @param url
         */
        void download(int id, int version, String url);

        /**
         * 暂停 继续
         * @param id
         * @param pause true 代表暂停  false 代表继续
         */
        void pauseContinue(int id, boolean pause);
    }

}
