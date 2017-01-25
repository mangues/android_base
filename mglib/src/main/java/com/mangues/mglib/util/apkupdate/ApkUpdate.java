package com.mangues.mglib.util.apkupdate;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import com.mangues.mglib.data.net.OkHttpClientManager;
import com.mangues.mglib.service.DownloadService;
import com.mangues.mglib.util.Dialog.DownloadDialog;
import com.mangues.mglib.util.Dialog.VersionUpateTipDialog;
import com.squareup.okhttp.Request;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mangues on 16/12/9.
 */

public class ApkUpdate {
    private static ApkUpdate apkUpdate;
    private Context mContext;
    private RealmUtil realmUtil;
    private String DB_NAME = "update_apk_db";
    private ApkUpdateCallBack apkUpdateCallBack;
    public static ApkUpdate getInstance(Context context){
        apkUpdate = new ApkUpdate(context);
        return apkUpdate;
    }

    public  ApkUpdate(Context context){
        mContext = context;
        realmUtil = new RealmUtil(context);
        Map<String, String> headers = new HashMap<>();
        headers.put("isMobile", "true");

    }


    private void checkApkUpdate(String url,String packName, OkHttpClientManager.ResultCallback<ApkUpdateInfo> callback){
        url = url +"?appId="+packName;
        OkHttpClientManager.getAsyn(url,callback);
    }


    private  Boolean isMainf = true;  //是否首页弹出
    private String baseUrl;
    public void checkApkVersion(String baseUrl,String url,final Boolean isMain) {
        isMainf = isMain;
        this.baseUrl = baseUrl;
        apkUpdate.checkApkUpdate(url,"com.may.xzcitycard", new OkHttpClientManager.ResultCallback<ApkUpdateInfo>() {
            @Override
            public void onError(Request request, Exception e) {
                if (!isMainf){
                    Toast.makeText(mContext,"当前已经是最新版本",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(ApkUpdateInfo response) {
                if (null != response&&response.getCode().equals("0000")){
                    boolean needUpata = VersionCheckTools.isNeedUpata(response.getReturnValue().getVersionCode(), mContext.getApplicationContext());
                    SharedPreferences sharedPreferences= mContext.getSharedPreferences("ApkUpdate",
                            Activity.MODE_PRIVATE);
                    Boolean isIgnore =sharedPreferences.getBoolean("version"+ response.getReturnValue().getVersionCode(), false);
                    Boolean isView = isIgnore;
                    if (!isMainf){
                        isView = false;
                    }

                    if (needUpata&&!isView){
                        //弹出更新提示
                        toUpdateApkTip(response,!isIgnore);
                    }else {
                        if (!isMainf) {
                            Toast.makeText(mContext, "当前已经是最新版本", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    if (!isMainf) {
                        Toast.makeText(mContext, "当前已经是最新版本", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void toUpdateApkTip(final ApkUpdateInfo apkUpdateInfo,Boolean isIgnore) {
        VersionUpateTipDialog versionUpateTipDialog = new VersionUpateTipDialog(mContext);
        versionUpateTipDialog.setOnUpdateListener(new VersionUpateTipDialog.OnUpdateListener() {
            @Override
            public void onUpdate() {
                if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
                    Toast.makeText(mContext,"该功能需要sd卡支持!",Toast.LENGTH_SHORT).show();
                }else {
                    toDownload(apkUpdateInfo);
                }
            }
        });
        versionUpateTipDialog.show();

        if (apkUpdateInfo.getReturnValue().isIsMustUpdate()){
            versionUpateTipDialog.setCancelButtonGone();
            versionUpateTipDialog.setCancelable(false);
            versionUpateTipDialog.setCanceledOnTouchOutside(false);
        }else{
            versionUpateTipDialog.setIgnoreVisible(isIgnore);
        }


        versionUpateTipDialog.setLatestVersionCode(apkUpdateInfo.getReturnValue().getVersionCode());
        versionUpateTipDialog.setLatestVersion("最新版本: "+apkUpdateInfo.getReturnValue().getVersionName());
        versionUpateTipDialog.setUpdateContent(""+apkUpdateInfo.getReturnValue().getUpdateInstructions());

    }

    private static final int DOWNLOAD_ID = 2;
    private ServiceConnection mConn;
    private void toDownload(final ApkUpdateInfo apkUpdateInfo) {
        if (null == realmUtil){
            realmUtil = new RealmUtil(mContext);
        }
        List<DownloadRecord> downloadRecords = realmUtil.find(DOWNLOAD_ID,apkUpdateInfo.getReturnValue().getVersionCode());
        DownloadRecord downloadRecord = null;
        if (null != downloadRecords && downloadRecords.size()>0){
            downloadRecord = downloadRecords.get(0);
            if (checkUpdate(downloadRecord)){
                return;
            }
        }

        DownloadDialog downloadDialog = new DownloadDialog(mContext);
        downloadDialog.setOnFinishListener(new DownloadDialog.OnFinishListener() {
            @Override
            public void onFinish(DownloadRecord downloadRecord) {
                mContext.unbindService(mConn);
                checkUpdate(downloadRecord);
            }
        });
        downloadDialog.show();

        Intent intent = new Intent(mContext, DownloadService.class);
        mConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DownloadService.MyBinder binder = (DownloadService.MyBinder) service;
                binder.download(DOWNLOAD_ID, apkUpdateInfo.getReturnValue().getVersionCode(), baseUrl+apkUpdateInfo.getReturnValue().getApkUrl());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        mContext.bindService(intent, mConn, Context.BIND_AUTO_CREATE);
    }

    private boolean checkUpdate(DownloadRecord downloadRecord) {
        String filePath = downloadRecord.getFilePath();
        PackageManager pm = mContext.getPackageManager();
        PackageInfo packageInfo = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        if (null == packageInfo){
            return false;
        }
        boolean needUpata = VersionCheckTools.isNeedUpata(packageInfo.versionCode, mContext.getApplicationContext());
        if (needUpata){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(filePath)),
                    "application/vnd.android.package-archive");
            mContext.startActivity(intent);
            return true;
        }
        return false;
    }

    public interface ApkUpdateCallBack{
        void onApkUpDateSuccess();
        void onApkUpDateError(String str);
    }


}
