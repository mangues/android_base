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

package com.mangues.lifecircleapp.data.net.retrofitrxjava;

import android.app.Activity;

import com.mangues.lifecircleapp.base.basemvp.MvpView;
import com.mangues.mglib.util.PubUtils;

import java.io.IOException;
import java.net.ConnectException;

import rx.functions.Action1;

/**
 * <br /> author: chenshufei
 * <br /> date: 16/3/15
 * <br /> email: chenshufei2@sina.com
 */
public abstract class ErrorAction implements Action1<Throwable> {

    private MvpView mvpView;

    public ErrorAction(MvpView mvpView){
        this.mvpView = mvpView;
    }

    @Override
    public void call(Throwable throwable) {
        if (null != mvpView){
            if (mvpView instanceof Activity){
                Activity activity = (Activity) mvpView;
                if (activity.isFinishing()) {
                    return;
                }
                if (!PubUtils.hasNetwork(activity)){
                    mvpView.onNoNetworkErrorTip();
                }else if(throwable instanceof ConnectException ||throwable instanceof IOException){
                    mvpView.onServerErrorTip();
                }else{
                    mvpView.onSystemException();
                }
            }
        }
        onCall(throwable);
    }

    public abstract void onCall(Throwable throwable);
}
