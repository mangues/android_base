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

import com.mangues.lifecircleapp.base.basemvp.BasePresenter;
import com.mangues.lifecircleapp.bean.LocationInfo;
import com.mangues.lifecircleapp.model.BaseRes;
import com.mangues.lifecircleapp.model.CircleModel;
import com.mangues.lifecircleapp.model.UserRes;
import com.mangues.lifecircleapp.data.cache.SecureSharedPreferences;
import com.mangues.lifecircleapp.data.enjine.GlobalVariables;
import com.mangues.lifecircleapp.data.net.retrofitrxjava.ErrorAction;
import com.mangues.lifecircleapp.data.net.retrofitrxjava.NetworkDateSource;
import com.mangues.lifecircleapp.data.net.retrofitrxjava.SubscribeResult;
import com.mangues.lifecircleapp.log.MLogger;
import com.mangues.lifecircleapp.mvpview.CircleMvpView;

import javax.inject.Inject;

import rx.Subscription;

/**
 * <br /> author: chenshufei
 * <br /> date: 16/1/18
 * <br /> email: chenshufei2@sina.com
 */
public class CirclePresenter extends BasePresenter<CircleMvpView> {

    private NetworkDateSource mNetworkDateSource;

    @Inject
    public CirclePresenter() {
        this.mNetworkDateSource = NetworkDateSource.getInstance();
    }

    public void circleList(final LocationInfo locationInfo) {
        getMvpView().showLoadingDialog();
        Subscription loginSubscription = mNetworkDateSource.circleList(locationInfo, new SubscribeResult<BaseRes<CircleModel>>() {
            @Override
            protected void onOk(BaseRes<CircleModel> response) {
                getMvpView().dismissLoadingDialog();
                getMvpView().onSuccess(response);
            }

            protected void onFailure(BaseRes<CircleModel> response) {
                getMvpView().dismissLoadingDialog();
                getMvpView().onError("失败");
            }
        }, new ErrorAction(getMvpView()) {
            @Override
            public void onCall(Throwable throwable) {
                getMvpView().dismissLoadingDialog();
                getMvpView().onError("失败");
            }
        });

        addSubscription(loginSubscription);

    }


}
