package com.mangues.lifecircleapp.data.enjine;

import com.mangues.lifecircleapp.bean.User;
import com.mangues.lifecircleapp.bean.UserRes;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * 全局 变量 保存处 ...， 原则上，应该只会有一个类 就是User
 * 防止  内存不够，从后台切换到前台时，数据被回收时，拿到空数据
 * <br /> author: chenshufei
 * <br /> date: 16/4/15
 * <br /> email: chenshufei2@sina.com
 */
public class GlobalVariables implements Serializable,Cloneable {

    private static GlobalVariables sGlobalVariables;
    private static final String CACHE_FILE_NAME = "GlobalVariables";
    private UserRes mUser;

    public static GlobalVariables getInstance(){
        if (null == sGlobalVariables){
            Object obj = SerializableUtil.restoreObject(CACHE_FILE_NAME);
            if (null == obj){
                sGlobalVariables = new GlobalVariables();
                SerializableUtil.saveObject(CACHE_FILE_NAME,sGlobalVariables);
            }else{
                sGlobalVariables = (GlobalVariables) obj;
            }
        }
        return sGlobalVariables;
    }

    public UserRes getUserRes() {
        return mUser;
    }

    public void setUserRes(UserRes user) {
        mUser = user;
        SerializableUtil.saveObject(CACHE_FILE_NAME,sGlobalVariables);
    }

    /**
     * 在app退出去清除掉
     */
    public void reset(){
        mUser = null;
        SerializableUtil.saveObject(CACHE_FILE_NAME,sGlobalVariables);
    }

    //------ 单例模式 保证本类对象的 序列化方法

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }

    public GlobalVariables readResolve() throws CloneNotSupportedException {
        sGlobalVariables = (GlobalVariables) this.clone();
        return sGlobalVariables;
    }

}
