package com.mangues.lifecircleapp.model;

import java.io.Serializable;

/**
 * Created by mangues on 16/7/27.
 */

public class UserRes implements Serializable {

    /**
     * Created by mangues on 16/7/27.
     */

    private User userModel;
    private String token;


    public User getUser() {
        return userModel;
    }

    public void setUser(User user) {
        this.userModel = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
