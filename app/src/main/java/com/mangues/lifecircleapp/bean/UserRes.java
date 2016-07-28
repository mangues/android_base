package com.mangues.lifecircleapp.bean;

import com.mangues.lifecircleapp.base.BaseBean;

import java.io.Serializable;

/**
 * Created by mangues on 16/7/27.
 */

public class UserRes implements Serializable {

    /**
     * Created by mangues on 16/7/27.
     */

    private User user;
    private String token;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
