package com.mangues.lifecircleapp.model;

import com.mangues.lifecircleapp.base.BaseBean;

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
