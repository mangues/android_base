
package com.mangues.mglib.base.basemvp;

/**
 * Presenter 主持者
 */
public interface Presenter<V extends MvpView> {
    void attachView(V mvpView);
    void detachView();
}
