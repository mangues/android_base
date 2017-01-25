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

package com.mangues.mglib.data.net;
import com.mangues.mglib.base.BaseBean;
import com.mangues.mglib.base.BaseRes;
import com.mangues.mglib.util.log.MLogger;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import okio.Buffer;
import okio.BufferedSource;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *
 */
public class NetworkDateSource {
    private final HttpApi mHttpApi;
    private static String BaseUrl;

    private static final Charset UTF8 = Charset.forName("UTF-8");

//    private static final NetworkDateSource sNetworkDateSource = new NetworkDateSource();

    static class Creator {

        public static HttpApi newHttpApi(String url) {

            OkHttpClient client = new OkHttpClient();
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

            client.setCookieHandler(cookieManager);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
//                    Request newRequest = SignUtil.getSignUrl(request);
                    Headers headers = request.headers();

                    MLogger.e("request url:"+request.toString());

//                    MLogger.e("request headers:");
                    Map<String, List<String>> headerListMap = headers.toMultimap();
                    for(Map.Entry<String, List<String>> entry : headerListMap.entrySet()){
                        List<String> values = entry.getValue();
                        StringBuffer sb = new StringBuffer();
                        for(String value : values){
                            sb.append(value).append(" ");
                        }
//                        MLogger.e("   "+entry.getKey()+"="+sb.toString());
                    }
//                    MLogger.e("request body:");


                    Response proceed = chain.proceed(request);
                    ResponseBody responseBody = proceed.body();
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();

                    Charset charset = UTF8;
                    MediaType contentType = responseBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(UTF8);
                    }
                    MLogger.e("返回值:");

                    if (responseBody.contentLength() != 0) {
                        //logger.log("");
                        //logger.log(buffer.clone().readString(charset));
                        MLogger.json(buffer.clone().readString(charset));
                    }
                    //L.e(proceed.networkResponse().body().string());
                    //L.e(proceed.body().string());
                    return proceed;
                }
            });
            //client.interceptors().add(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(HttpApi.class);
        }
    }

    private NetworkDateSource(String baseUrl){
        mHttpApi = Creator.newHttpApi(baseUrl);
    }
    private static NetworkDateSource sNetworkDateSource;


    public static void register(String baseUrl){
        BaseUrl = baseUrl;
    }


    public static NetworkDateSource getInstance(){
        if (BaseUrl==null){
            MLogger.e("NetworkDateSource","请在Application中调用register注册");

        }else {
            if (sNetworkDateSource == null) {
                synchronized (NetworkDateSource.class) {
                    if (sNetworkDateSource == null) {
                        sNetworkDateSource = new NetworkDateSource(BaseUrl);
                        return sNetworkDateSource;
                    }
                }
            }
        }

        return sNetworkDateSource;
    }



//    public Subscription downloadFile(String url,String range, Action1<ResponseBody> action1, Action1<Throwable> onError){
//        return doSubscription(mHttpApi.downloadFile(url,range),action1,onError);
//    }






    // 不强制 T extends BaseBean 了
    private  <T> Subscription doSubscription(Observable<T> observable, Action1<T> action1, Action1<Throwable> onError){
        if (null != onError){
            return observable.observeOn(AndroidSchedulers.mainThread()) //处理的结果 给 订阅者返回是在主线程的
                    .subscribeOn(Schedulers.io()) //被观察者observable是在io线程里处理的，
                    .subscribe(action1,onError);
        }else{
            return observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(action1);
        }
    }






}
