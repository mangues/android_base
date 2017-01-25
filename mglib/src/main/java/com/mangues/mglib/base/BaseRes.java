package com.mangues.mglib.base;

/**
 * Created by mangues on 16/7/27.
 */

public class BaseRes<T> extends BaseBean {
    T oj;

    public T getOj() {
        return oj;
    }

    public void setOj(T oj) {
        this.oj = oj;
    }
}
