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

package com.mangues.mglib.util.apkupdate;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * <br /> author: chenshufei
 * <br /> date: 16/7/28
 * <br /> email: chenshufei2@sina.com
 */
public class VersionCheckTools {
    /**
     * 是否需要版本升级,是代表需要,本地的版本号低于传入进来的version。
     * 否代表不需要
     * @param version
     * @return
     */
    public static boolean isNeedUpata(int version,Context context){
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (null != packageInfo){
            int localVersion = packageInfo.versionCode;
            return version > localVersion;
        }else{
            return false;
        }
    }

    /**
     * 获取版本名称
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (null != packageInfo){
            return packageInfo.versionName;
        }else{
            return "";
        }
    }

    /**
     * 获取包名
     * @param context
     * @return
     */
    public static String getPackageName(Context context){
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (null != packageInfo){
            return packageInfo.packageName;
        }else{
            return "";
        }
    }
}
