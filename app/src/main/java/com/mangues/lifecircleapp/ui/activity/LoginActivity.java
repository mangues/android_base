package com.mangues.lifecircleapp.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.mangues.lifecircleapp.R;
import com.mangues.lifecircleapp.base.basemvp.BasePresenter;
import com.mangues.lifecircleapp.mvpview.BaseMvpView;
import com.mangues.lifecircleapp.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements BaseMvpView{

    @Inject
    LoginPresenter loginPresenter;
    @Bind(R.id.login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_login);
    }

    @OnClick(R.id.login)
    public void onClick() {
        loginPresenter.login("xutao","123456");
    }


    @Override
    public void onSuccess(Object t) {

    }

    @Override
    public void onError(Object error) {

    }


    @Override
    protected BasePresenter[] initPresenters() {
        return new BasePresenter[]{loginPresenter};
    }

    @Override
    protected CharSequence getTopTitle() {
        return "登录";
    }

    @Override
    protected boolean isBack() {
        return true;
    }
}
