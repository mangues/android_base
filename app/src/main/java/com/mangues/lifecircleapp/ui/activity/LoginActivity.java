package com.mangues.lifecircleapp.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mangues.lifecircleapp.R;
import com.mangues.lifecircleapp.base.basemvp.BasePresenter;
import com.mangues.lifecircleapp.mvpview.LoginMvpView;
import com.mangues.lifecircleapp.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginPresenter loginPresenter;

    @Bind(R.id.login)
    Button login;
    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.iv_right_btn)
    ImageView ivRightBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarTintResource(R.color.white, false);
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_login);
        setDisableNavigationIcon();

    }

    @OnClick({R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                String userName = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    showToast("用户名密码不为空");
                    return;
                }
                loginPresenter.login(userName, password);
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
        return new BasePresenter[]{loginPresenter};
    }

    @Override
    protected CharSequence getTopTitle() {
        return null;
    }


    @OnClick(R.id.iv_right_btn)
    public void onClick() {
        finish();
    }
}
