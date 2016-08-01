package com.mangues.lifecircleapp.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.mangues.lifecircleapp.LifeCircleApplication;
import com.mangues.lifecircleapp.injection.module.FragmentModule;
import com.mangues.lifecircleapp.R;
import com.mangues.lifecircleapp.base.basemvp.BasePresenter;
import com.mangues.lifecircleapp.base.basemvp.MvpView;
import com.mangues.lifecircleapp.log.MLogger;
import com.mangues.lifecircleapp.ui.activity.BaseActivity;
import com.mangues.lifecircleapp.util.Dialog.DialogUtils;
import com.mangues.lifecircleapp.util.JSToast;
import com.mangues.lifecircleapp.util.PubUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.mangues.lifecircleapp.injection.component.FrgmentComponent;
import butterknife.ButterKnife;
import com.mangues.lifecircleapp.injection.component.DaggerFrgmentComponent;
//import com.robin.lazy.logger.LazyLogger;

/**
 * Created by mangues on 16/7/12.
 */

public abstract class BaseFragement extends Fragment implements MvpView{
    protected String TAG =      "BaseFragement";
    protected ImageLoader imageLoader;
    private Dialog progressDialog;
    private Context mContext;
    private BasePresenter[] mPresenters;


    private int mNavigationIcon;
    private Drawable mNavigationDrawable;


    private Toolbar mToolbar;
    private TextView mTitleTextView;
    private ImageView mIv_center_log;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBarTint){
            applyKitKatTranslucency();
        }
        mContext  = getActivity();
        init();
    }

    View view;



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mPresenters = initPresenters();
        //将attachView放在base中搞定
        if (null != mPresenters && mPresenters.length > 0){
            for (BasePresenter presenter : mPresenters){
                if (this instanceof MvpView){
                    presenter.attachView((MvpView)this);
                }
            }
        }
        View toolView = view.findViewById(R.id.toolbar);
        if (null != toolView){
            mToolbar = (Toolbar) toolView;
            FragmentActivity activity = getActivity();
            if (activity instanceof AppCompatActivity){
                AppCompatActivity appActivity = (AppCompatActivity) activity;
                appActivity.setSupportActionBar(mToolbar);
            }


            mTitleTextView = (TextView) toolView.findViewById(R.id.tv_title);
            mIv_center_log = (ImageView) toolView.findViewById(R.id.iv_center_log);

            CharSequence title = getTopTitle();
            if (null != title && title.length()>0){
                mTitleTextView.setText(title);
            }

            mToolbar.setNavigationIcon(mNavigationIcon);
            if (null != mNavigationDrawable){
                mToolbar.setNavigationIcon(mNavigationDrawable);
            }
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickNavigationIcon();
                }
            });

        }


        mPresenters = initPresenters();
        //将attachView放在base中搞定
        if (null != mPresenters && mPresenters.length > 0){
            for (BasePresenter presenter : mPresenters){
                if (this instanceof MvpView){
                    presenter.attachView((MvpView)this);
                }
            }
        }
    }


    public FrgmentComponent getComponent(){
        FrgmentComponent component = DaggerFrgmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .applicationComponent(LifeCircleApplication.getInstance().getComponent())
                .build();
        return component;
    }

    protected abstract CharSequence getTopTitle();

    protected void onClickNavigationIcon() {
    }



    private void init(){
        TAG = getClass().getSimpleName();
        imageLoader = ImageLoader.getInstance();
        mNavigationIcon = R.drawable.common_bg_top_back;
        progressDialog = DialogUtils.createProgressDialog(mContext);
        MLogger.i(TAG+"初始化完成");
    }

    /**
     * 子类根据实现情况，重写此方法提供presenter出去
     * 若比较简单的页面，直接写死说明，从需要外部数据时，就不需要重写此方法
     * @return
     */
    protected BasePresenter[] initPresenters() {
        return null;
    }




    //设置导航icon，通常都是返回icon
    protected void setNavigationIcon(int iconResourceId){
        mNavigationIcon = iconResourceId;
    }
    protected void setNavigationIcon(Drawable navigationDrawable){
        mNavigationDrawable = navigationDrawable;
    }

    protected void setDisableNavigationIcon(){
        mToolbar.setNavigationIcon(null);
    }




    protected String getTitle() {
            return "";
    }

    protected void showLog(String msg) {
        MLogger.i(TAG, msg);
    }
    protected void showToast(String msg) {
      JSToast.show(mContext, msg, Toast.LENGTH_SHORT);
    }
    protected void showProgressDialog() {
        progressDialog.show();
    }
    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }




    private SimpleArcDialog mDialog;
    @Override
    public void showLoadingDialog() {
        if (null == mDialog){
            mDialog = new SimpleArcDialog(getActivity());
            mDialog.setConfiguration(new ArcConfiguration(getActivity()));
        }
        mDialog.show();

    }

    @Override
    public void dismissLoadingDialog() {
        if (null != mDialog ){
            mDialog.cancel();
            mDialog = null;
        }
    }

    @Override
    public void onUnLogin() {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity){
            BaseActivity baseActivity = (BaseActivity) activity;
            baseActivity.onUnLogin();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (null != mPresenters && mPresenters.length > 0){
            for (BasePresenter presenter : mPresenters){
                presenter.detachView();
            }
        }
        dismissLoadingDialog();
    }

    @Override
    public void onNoNetworkErrorTip() {
        PubUtils.popTipOrWarn(getActivity(), "暂无网络连接！");
    }

    @Override
    public void onServerErrorTip() {
        PubUtils.popTipOrWarn(getActivity(), "服务器忙，请稍候再试！");
    }

    @Override
    public void onSystemException() {
        PubUtils.popTipOrWarn(getActivity(), "系统错误");
    }

    public void launch(Class<? extends Activity> clazz) {
        launch(new Intent(getActivity(), clazz));
    }

    public void launch(Intent intent) {
        startActivity(intent);
    }

    public <T extends View> T getViewById(View view, int id) {
        return (T) view.findViewById(id);
    }


    private int color = R.color.colorPrimary;
    private boolean isBarTint = true;
    protected void  setStatusBarTintResource(int color,boolean isBarTint){
        this.color = color;
        this.isBarTint = isBarTint;
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
        mTintManager.setStatusBarTintResource(color);//通知栏所需颜色

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
