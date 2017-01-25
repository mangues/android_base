package com.mangues.mglib.util.Dialog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mangues.mglib.R;
import com.mangues.mglib.dialog.BaseDialog;
public class VersionUpateTipDialog extends BaseDialog implements View.OnClickListener{

     TextView tv_latest_version;
    TextView tv_updatecontent;
    CheckBox cb_ignore;
    Button btn_cancel;
    Button btn_update;
    private OnUpdateListener mOnUpdateListener;
    private boolean isIgnore = false; //是否忽略该版本,默念不忽略,升级
    private int mLatestVersionCode; //最新版本code
    private Context context;
    public VersionUpateTipDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_versionupdatetip);
        setCanceledOnTouchOutside(false);
        tv_latest_version = (TextView)findViewById(R.id.tv_latest_version);
        tv_updatecontent = (TextView)findViewById(R.id.tv_updatecontent);
        cb_ignore = (CheckBox)findViewById(R.id.cb_ignore);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_update = (Button)findViewById(R.id.btn_update);
        btn_cancel.setOnClickListener(this);
        btn_update.setOnClickListener(this);

//        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
    }

    public void setLatestVersion(String latestVersion){
        tv_latest_version.setText(latestVersion);
    }

    public void setLatestVersionCode(int latestVersionCode){
        this.mLatestVersionCode = latestVersionCode;
    }

    public void setUpdateContent(String content){
        if (!TextUtils.isEmpty(content)){
            content = content.replace("\\n","\n");
            tv_updatecontent.setText(content);
        }
    }

    /**
     * 是否忽略该版
     * @param ignoreVisible
     */
    public void setIgnoreVisible(boolean ignoreVisible){
        cb_ignore.setVisibility(ignoreVisible? View.VISIBLE: View.GONE);
    }

    public void setCancelButtonGone(){
        btn_cancel.setVisibility(View.GONE);
    }

    public void onClick(View v){
        int i = v.getId();
        if (i == R.id.btn_update) {
            if (null != mOnUpdateListener) {
                mOnUpdateListener.onUpdate();
            }
            dismiss();

        } else if (i == R.id.btn_cancel) {
            boolean checked = cb_ignore.isChecked();
            if (checked && mLatestVersionCode > 0) {
                SharedPreferences mySharedPreferences= context.getSharedPreferences("ApkUpdate",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putBoolean("version"+mLatestVersionCode, true);
                editor.commit();
            }
            dismiss();

        }
    }

    public interface OnUpdateListener{
        void onUpdate();
    }


    public void setOnUpdateListener(OnUpdateListener onUpdateListener){
        this.mOnUpdateListener = onUpdateListener;
    }

}
