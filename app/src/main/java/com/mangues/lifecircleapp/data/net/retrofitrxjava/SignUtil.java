package com.mangues.lifecircleapp.data.net.retrofitrxjava;

import com.mangues.lifecircleapp.data.cache.SecureSharedPreferences;
import com.mangues.lifecircleapp.data.net.Constant;
import com.mangues.lifecircleapp.util.Md5;
import com.squareup.okhttp.Request;

/**
 * Created by mangues on 16/7/29.
 */

public class SignUtil {
    private static String getSign(String url,String userId){
        String token = SecureSharedPreferences.getString("token");
        url = url+"&userId="+userId+"&token="+token;
        String trueSign = Md5.getMD5(url);
        return trueSign;
    }


    public static Request getSignUrl(Request originalRequest){
        String userId = SecureSharedPreferences.getString("userId");
        String url = originalRequest.urlString();
        String urlPath = url.replace(Constant.BASE_URL,"/");
        String sign = getSign(urlPath,userId);

        Request newRequest = originalRequest.newBuilder()
                .url(url+"&userId="+userId)
                //.addHeader("accept", "application/json")
                .addHeader("Content-Language", "zh-CN")
                .addHeader("Accept-Language", "zh-CN")
                .addHeader("sign",sign)
                .build();

        return newRequest;
    }
}

