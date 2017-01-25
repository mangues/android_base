package com.mangues.mglib.data.net;

import com.mangues.mglib.base.BaseBean;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.ResponseBody;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.Streaming;
import retrofit.http.Url;
import rx.Observable;

/**
 * Created by mangues on 17/1/6.
 */

public interface HttpApi {
        @POST("app/test")
        Observable<BaseBean> test(@Query("name") String username, @Query("password") String password);
}
