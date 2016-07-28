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

package com.mangues.lifecircleapp.data.net.retrofitrxjava;

import com.mangues.lifecircleapp.base.BaseBean;
import com.mangues.lifecircleapp.bean.BaseRes;
import com.mangues.lifecircleapp.bean.UserRes;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.http.Url;
import rx.Observable;


/**
 * <br /> author: chenshufei
 * <br /> date: 16/1/6
 * <br /> email: chenshufei2@sina.com
 */
public interface HttpApi {

    @POST("app/login")
    Observable<BaseRes<UserRes>> login(@Query("name") String username, @Query("password") String password);


//    /**
//     * 获取验证码并发送短信到手机上
//     *
//     * @param phone 手机号码
//     * @param type  业务类型
//     */
//    @POST("getVerificationCode")
//    Observable<BaseBean> getVerificationCode(@Query("phone") String phone, @Query("type") String type);
//
//    @POST("user/applyStore?")
//    Observable<BaseBean> getMerChantVerificationCode(@Query("phone") String phone, @Query("name") String name);
//
//    @POST("register")
//    Observable<UserRes> register(@Query("nickName") String nickName, @Query("phone") String phone, @Query("password") String password, @Query("verificationCode") String verificationCode, @Query("isAutoLogin") boolean isAutoLogin);
//
//
//    /**
//     * POST /file/uploadFile
//     * 上传文件
//     *
//     * @param file
//     * @return
//     */
//    @Multipart
//    @POST("file/uploadFile")
//    Observable<BaseRes<String>> uploadFile(@Part("file\"; filename=\"image.jpg\"") RequestBody file);
//
//
//    /**
//     * 下载 图片
//     *
//     * @param fileUrl
//     * @return
//     */
//    @GET
//    Observable<ResponseBody> downloadPicFromNet(@Url String fileUrl);

}
