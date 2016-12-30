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

package com.mangues.lifecircleapp.presenter;

import com.mangues.lifecircleapp.base.BaseBean;
import com.mangues.lifecircleapp.base.basemvp.BasePresenter;
import com.mangues.lifecircleapp.data.net.retrofitrxjava.ErrorAction;
import com.mangues.lifecircleapp.data.net.retrofitrxjava.NetworkDateSource;
import com.mangues.lifecircleapp.data.net.retrofitrxjava.SubscribeResult;
import com.mangues.lifecircleapp.mvpview.LoginMvpView;
import com.mangues.mglib.util.log.MLogger;

import javax.inject.Inject;

import rx.Subscription;

/**
 * <br /> author: chenshufei
 * <br /> date: 16/1/18
 * <br /> email: chenshufei2@sina.com
 */
public class TestPresenter extends BasePresenter<LoginMvpView> {

    private NetworkDateSource mNetworkDateSource;

    @Inject
    public TestPresenter() {
        this.mNetworkDateSource = NetworkDateSource.getInstance();
    }

    public void test(final String username, final String password) {
        getMvpView().showLoadingDialog();
        Subscription loginSubscription = mNetworkDateSource.test(username, password, new SubscribeResult<BaseBean>() {
            @Override
            protected void onOk(BaseBean response) {
                getMvpView().dismissLoadingDialog();
                getMvpView().onSuccess(response);
            }

            protected void onFailure(BaseBean response) {
                getMvpView().dismissLoadingDialog();
                MLogger.d("2",this);
                getMvpView().onError("失败");
            }
        }, new ErrorAction(getMvpView()) {
            @Override
            public void onCall(Throwable throwable) {
                getMvpView().dismissLoadingDialog();
                MLogger.d("3",this);
                getMvpView().onError("失败");
            }
        });

        addSubscription(loginSubscription);

    }


}
