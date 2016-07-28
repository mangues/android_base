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

package com.mangues.lifecircleapp.base.basemvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 
 * <br /> author: chenshufei
 * <br /> date: 16/1/15
 * <br /> email: chenshufei2@sina.com
 */
public class BasePresenter<V extends MvpView> implements Presenter<V> {

    private V mMvpView;
    private CompositeSubscription mCompositeSubscription;

    public BasePresenter(){

    }

    protected void addSubscription(Subscription subscription){
        if (null == mCompositeSubscription){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void attachView(V mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
        if (null != mCompositeSubscription){
            mCompositeSubscription.unsubscribe();
        }
    }

    public boolean isViewAttached(){
        return mMvpView != null;
    }

    public V getMvpView(){
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }



}
