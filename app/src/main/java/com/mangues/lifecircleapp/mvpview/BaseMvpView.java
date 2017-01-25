package com.mangues.lifecircleapp.mvpview;

import com.mangues.mglib.base.basemvp.MvpView;

/**
 * Created by mangues on 16/7/27.
 */
public interface BaseMvpView extends MvpView{
    void onSuccess(Object t);
    void onError(Object error);
}
