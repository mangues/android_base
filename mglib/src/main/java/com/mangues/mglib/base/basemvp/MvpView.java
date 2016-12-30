
package com.mangues.mglib.base.basemvp;

public interface MvpView {
    void showLoadingDialog();

    void dismissLoadingDialog();

    void onUnLogin();

    void onNoNetworkErrorTip();

    void onServerErrorTip();

    void onSystemException();

}
