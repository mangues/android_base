package com.mangues.mglib.util;

import android.support.annotation.NonNull;

/**
 * Created by mangues on 17/1/24.
 */

public class UrlUtil {
    @NonNull
    public static String getFileName(String url){
        int index = url.lastIndexOf("/");
        return url.substring(index+1, url.length());
    }
}
