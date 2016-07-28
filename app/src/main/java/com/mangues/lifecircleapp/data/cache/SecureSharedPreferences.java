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

package com.mangues.lifecircleapp.data.cache;

import android.content.SharedPreferences;

import com.mangues.lifecircleapp.LifeCircleApplication;


/**
 * 获取安全的SharePreferences，采用RSA 加salt及向量的 对key与value一起加密的SharePreferences
 * <br /> author: chenshufei
 * <br /> date: 15/10/9
 * <br /> email: chenshufei2@sina.com
 */
public class SecureSharedPreferences {
    
    //安全的SharePreferences，采用RSA 加salt及向量的 对key与value一起加密的SharePreferences
    private static SecurePreferences securePrefs;
    static {
        securePrefs = new SecurePreferences(LifeCircleApplication.getInstance(), "", "my_pref.xml");
        SecurePreferences.setLoggingEnabled(true);
    }

    public static void putString(String key, String value){
        SharedPreferences.Editor editor = securePrefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putInt(String key, int value){
        SharedPreferences.Editor editor = securePrefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putBoolean(String key, boolean value){
        SharedPreferences.Editor editor = securePrefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static int getInt(String key){
        return securePrefs.getInt(key, 0);
    }

    public static int getInt(String key, int default_value){
        return securePrefs.getInt(key,default_value);
    }

    public static String getString(String key){
        return securePrefs.getString(key, "");
    }

    public static String getString(String key, String default_value){
        return securePrefs.getString(key, default_value);
    }

    public static boolean getBoolean(String key, boolean defValue){
        return securePrefs.getBoolean(key, defValue);
    }

}
