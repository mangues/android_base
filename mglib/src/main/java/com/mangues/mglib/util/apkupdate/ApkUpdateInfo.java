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


import java.io.Serializable;

/**
 * <br /> author: chenshufei
 * <br /> date: 16/8/11
 * <br /> email: chenshufei2@sina.com
 */
public class ApkUpdateInfo implements Serializable {

    /**
     * message : 操作成功
     * code : 0000
     * returnValue : {"id":1972103167640576,"appId":"com.may.xzcitycard","versionCode":10,"versionName":"10.0.1","isMustUpdate":true,"updateInstructions":"com.may.xzcitycard","apkUrl":"/upload/20161219122246809/FF0826F24B52125B87C18C5842A1539C.m","createTime":"20161219122246806"}
     */

    private String message;
    private String code;
    /**
     * id : 1972103167640576
     * appId : com.may.xzcitycard
     * versionCode : 10
     * versionName : 10.0.1
     * isMustUpdate : true
     * updateInstructions : com.may.xzcitycard
     * apkUrl : /upload/20161219122246809/FF0826F24B52125B87C18C5842A1539C.m
     * createTime : 20161219122246806
     */

    private ReturnValueBean returnValue;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ReturnValueBean getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ReturnValueBean returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private long id;
        private String appId;
        private int versionCode;
        private String versionName;
        private boolean isMustUpdate;
        private String updateInstructions;
        private String apkUrl;
        private String createTime;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public boolean isIsMustUpdate() {
            return isMustUpdate;
        }

        public void setIsMustUpdate(boolean isMustUpdate) {
            this.isMustUpdate = isMustUpdate;
        }

        public String getUpdateInstructions() {
            return updateInstructions;
        }

        public void setUpdateInstructions(String updateInstructions) {
            this.updateInstructions = updateInstructions;
        }

        public String getApkUrl() {
            return apkUrl;
        }

        public void setApkUrl(String apkUrl) {
            this.apkUrl = apkUrl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
