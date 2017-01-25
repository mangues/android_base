/*
 * Copyright 2016. chenshufei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mangues.mglib.util.Dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.Formatter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mangues.mglib.R;
import com.mangues.mglib.dialog.BaseDialog;
import com.mangues.mglib.service.DownloadService;
import com.mangues.mglib.util.apkupdate.DownloadRecord;

import java.lang.ref.SoftReference;

/**
 * <br /> author: chenshufei
 * <br /> date: 16/7/29
 * <br /> email: chenshufei2@sina.com
 */
public class DownloadDialog extends BaseDialog {

    ProgressBar pb_progress;
    TextView tv_percent;
    TextView tv_progress;
    private LocalBroadcastManager mBroadcastManager;
    private DownloadHandler mDownloadHanlder;
    private DownloadReceiver mReceiver;
    private OnFinishListener mOnFinishListener;

    public DownloadDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_download);
        mBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        mDownloadHanlder = new DownloadHandler(this);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        pb_progress = (ProgressBar)findViewById(R.id.pb_progress);
        tv_percent = (TextView)findViewById(R.id.tv_percent);
        tv_progress = (TextView)findViewById(R.id.tv_progress);



    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadService.DOWNLOAD_RECEIVER_ACTION);

        mReceiver = new DownloadReceiver(this);
        getContext().registerReceiver(mReceiver,intentFilter);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(mReceiver);
    }

    public DownloadHandler getDownloadHanlder() {
        return mDownloadHanlder;
    }

    private void handlerDownloadRecord(DownloadRecord downloadRecord){
        long totalLen = downloadRecord.getTotalLen();
        long finishLen = downloadRecord.getFinishLen();
        long precent = finishLen*100/totalLen;
        pb_progress.setMax((int) totalLen);
        pb_progress.setProgress((int) finishLen);
        tv_percent.setText(precent+"%");
        String totalLenStr = Formatter.formatFileSize(getContext().getApplicationContext(), totalLen);
        String finishLenStr = Formatter.formatFileSize(getContext().getApplicationContext(), finishLen);
        tv_progress.setText(finishLenStr+"/"+totalLenStr);
        if (DownloadService.DOWNLOAD_FINISH_MSG == downloadRecord.getDownloadStatus()){
            if (null != mOnFinishListener){
                mOnFinishListener.onFinish(downloadRecord);
            }
            dismiss();
        }
    }

    public void setOnFinishListener(OnFinishListener onFinishListener){
        this.mOnFinishListener = onFinishListener;
    }

    public interface OnFinishListener{
        void onFinish(DownloadRecord downloadRecord);
    }


    private static class DownloadHandler extends Handler {
        private SoftReference<DownloadDialog> mSoftReference;

        public DownloadHandler(DownloadDialog dialog){
            mSoftReference = new SoftReference<DownloadDialog>(dialog);
        }
        @Override
        public void handleMessage(Message msg) {
            DownloadDialog downloadDialog = mSoftReference.get();
            DownloadRecord downloadRecord = (DownloadRecord) msg.obj;
            if (null != downloadDialog){
                downloadDialog.handlerDownloadRecord(downloadRecord);
            }
        }
    }

    private static class DownloadReceiver extends BroadcastReceiver {

        private SoftReference<DownloadDialog> mSoftReference;

        public DownloadReceiver(DownloadDialog dialog){
            mSoftReference = new SoftReference<DownloadDialog>(dialog);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            DownloadRecord downloadRecord = (DownloadRecord) intent.getSerializableExtra("download_record");
            DownloadDialog downloadDialog = mSoftReference.get();
            if (null != downloadDialog){
                DownloadHandler downloadHanlder = downloadDialog.getDownloadHanlder();
                Message message = downloadHanlder.obtainMessage();
                message.obj = downloadRecord;
                message.what = 100;
                downloadHanlder.sendMessage(message);
            }
        }
    }





}
