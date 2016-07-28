package com.mangues.lifecircleapp.fragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.mangues.lifecircleapp.R;
import com.mangues.lifecircleapp.log.MLogger;
import com.mangues.lifecircleapp.util.Dialog.DialogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;
import com.readystatesoftware.systembartint.SystemBarTintManager;
//import com.robin.lazy.logger.LazyLogger;

/**
 * Created by mangues on 16/7/12.
 */

public abstract class BaseFragement extends Fragment {
    private Toolbar mToolbar;
    protected String TAG =      "BaseFragement";
    protected ImageLoader imageLoader;
    private Dialog progressDialog;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyKitKatTranslucency();
        mContext  = getActivity();
        init();
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = getLayoutView(inflater,container);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return view;
    }



    private void init(){
        TAG = getClass().getSimpleName();
        imageLoader = ImageLoader.getInstance();
        progressDialog = DialogUtils.createProgressDialog(mContext);
        MLogger.i(TAG+"初始化完成");
    }




    protected String getTitle() {
            return "";
    }

    protected abstract View  getLayoutView(LayoutInflater inflater,ViewGroup container);
    protected void showLog(String msg) {
        MLogger.i(TAG, msg);
    }
//    protected void showToast(String msg) {
//       com.mangues.lifecircleapp.Util.JSToast.show(mContext, msg, Toast.LENGTH_SHORT);
//    }
    protected void showProgressDialog() {
        progressDialog.show();
    }
    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    /**
     * Apply KitKat specific translucency.
     */
    private void applyKitKatTranslucency() {
        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);

        }
        SystemBarTintManager mTintManager = new SystemBarTintManager(getActivity());
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
//           mTintManager.setTintColor(getResources().getColor(R.color.colorAccent));
        mTintManager.setStatusBarTintResource(R.color.colorAccent);//通知栏所需颜色

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
