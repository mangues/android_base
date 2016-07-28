package com.mangues.lifecircleapp.ui.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.mangues.lifecircleapp.injection.component.DaggerActivityComponent;
import com.mangues.lifecircleapp.LifeCircleApplication;
import com.mangues.lifecircleapp.base.basemvp.BasePresenter;
import com.mangues.lifecircleapp.base.basemvp.MvpView;
import com.mangues.lifecircleapp.bean.MessageEvent;
import com.mangues.lifecircleapp.injection.component.ActivityComponent;
import com.mangues.lifecircleapp.injection.module.ActivityModule;
import com.mangues.lifecircleapp.log.MLogger;
import com.mangues.lifecircleapp.util.*;

import com.mangues.lifecircleapp.R;
import com.mangues.lifecircleapp.util.Dialog.DialogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;
//import com.robin.lazy.logger.LazyLogger;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by mangues on 16/7/8.
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView {
    private Toolbar mToolbar;
    protected String TAG =      "BaseActivity";
    protected ImageLoader       imageLoader;
    private Dialog progressDialog;

    private BasePresenter[] mPresenters;
    public Context mContext;
    private int mNavigationIcon;
    private Drawable mNavigationDrawable;

    protected EventBus eventBus;

    private TextView mTitleTextView;
    private ImageView mIv_center_log;

//    private NetRequestTipsDialog mNetRequestTipsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyKitKatTranslucency();
        init();
    }



    private void init(){
        mNavigationIcon = R.drawable.common_bg_top_back;
        mContext =this;
        LifeCircleApplication.getInstance().addActivity(this);
        eventBus = EventBus.getDefault();
        eventBus.register(this);


        TAG = getClass().getSimpleName();
        imageLoader = ImageLoader.getInstance();
        progressDialog = DialogUtils.createProgressDialog(this);
        MLogger.i(TAG,"初始化完成");
    }


    protected void initToolbar(boolean displayHomeAsUp,String title){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUp);
    }


    public void onEvent(MessageEvent event) {

    }

    /**
     * 解决activity 依赖的注入 组件
     * @return
     */
    public ActivityComponent getComponent(){
        ActivityComponent component = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(LifeCircleApplication.getInstance().getComponent())
                .build();
        return component;
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        View toolView = findViewById(R.id.toolbar);
        if (null != toolView){
            mToolbar = (Toolbar) toolView;
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isBack());
            mTitleTextView = (TextView) toolView.findViewById(R.id.tv_title);
            mIv_center_log = (ImageView) toolView.findViewById(R.id.iv_center_log);

            CharSequence title = getTopTitle();
            if (null != title && title.length()>0){
                mTitleTextView.setText(title);
                mToolbar.setTitle(title);
            }

//            mToolbar.setNavigationIcon(mNavigationIcon);
//            if (null != mNavigationDrawable){
//                mToolbar.setNavigationIcon(mNavigationDrawable);
//            }
//            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onClickNavigationIcon();
//                }
//            });

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
    protected abstract CharSequence getTopTitle() ;
    protected abstract boolean isBack() ;


    /**
     * 子类根据实现情况，重写此方法提供presenter出去
     * 若比较简单的页面，直接写死说明，从需要外部数据时，就不需要重写此方法
     * @return
     */
    protected  BasePresenter[] initPresenters(){
        return null;
    };


    protected void onClickNavigationIcon() {
        finish();
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

    //子类，自己setTitle进去...

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        LifeCircleApplication.getInstance().removeActivity(this);
        if (null != mPresenters && mPresenters.length > 0){
            for (BasePresenter presenter : mPresenters){
                presenter.detachView();
            }
        }
        dismissLoadingDialog();
        eventBus.unregister(this);
    }




    protected void showLog(String msg) {
        MLogger.i(TAG, msg);
    }
    protected void showToast(String msg) {
       JSToast.show(this, msg, Toast.LENGTH_SHORT);
    }
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
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
//           mTintManager.setTintColor(getResources().getColor(R.color.colorAccent));
        mTintManager.setStatusBarTintResource(R.color.colorAccent);//通知栏所需颜色

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    private SimpleArcDialog mDialog;
    @Override
    public void showLoadingDialog() {
        if (null == mDialog){
            mDialog = new SimpleArcDialog(this);
            mDialog.setConfiguration(new ArcConfiguration(this));
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

    }

    @Override
    public void onNoNetworkErrorTip() {
        PubUtils.popTipOrWarn(this, "暂无网络连接！");
    }

    @Override
    public void onServerErrorTip() {
        PubUtils.popTipOrWarn(this, "服务器忙，请稍候再试！");
    }

    @Override
    public void onSystemException() {
        PubUtils.popTipOrWarn(this, "系统错误");
    }
}
