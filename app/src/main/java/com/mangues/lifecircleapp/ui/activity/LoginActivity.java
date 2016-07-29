package com.mangues.lifecircleapp.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mangues.lifecircleapp.R;
import com.mangues.lifecircleapp.base.basemvp.BasePresenter;
import com.mangues.lifecircleapp.mvpview.BaseMvpView;
import com.mangues.lifecircleapp.mvpview.LoginMvpView;
import com.mangues.lifecircleapp.presenter.LoginPresenter;
import com.mangues.lifecircleapp.presenter.TestPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginMvpView{

    @Inject
    LoginPresenter loginPresenter;
    @Inject
    TestPresenter testPresenter;
    @Bind(R.id.login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_login);
    }

    @OnClick({R.id.login,R.id.test})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                loginPresenter.login("xutao","123456");
                break;

            case R.id.test:
                testPresenter.test("xutao","123456");
                break;
        }
    }


    @Override
    public void onSuccess(Object t) {

    }

    @Override
    public void onError(Object error) {

    }


    @Override
    protected BasePresenter[] initPresenters() {
        return new BasePresenter[]{loginPresenter,testPresenter};
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
