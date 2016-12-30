package com.mangues.mglib.util.log;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by mangues on 16/7/27.
 */

public class MLogger {
    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

//    public static void d(Throwable throwable, String message, Object... args) {
//        Logger.d(throwable, message, args);
//
//    }

    public static void e(String message, Object... args) {
        Logger.e(null, message, args);
    }

//    public static void e(Throwable throwable, String message, Object... args) {
//        LazyLogger.e(throwable, message, args);
//    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

//    public static void i(Throwable throwable, String message, Object... args) {
//        LazyLogger.i(throwable, message, args);
//    }

    public static void v(String message, Object... args) {
        Logger.v(message, args);
    }

//    public static void v(Throwable throwable, String message, Object... args) {
//        LazyLogger.v(throwable, message, args);
//    }

    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

//    public static void w(Throwable throwable, String message, Object... args) {
//        LazyLogger.w(throwable, message, args);
//    }

    public static void wtf(String message, Object... args) {
        Logger.wtf(message, args);
    }

//    public static void wtf(Throwable throwable, String message, Object... args) {
//        Logger.wtf(throwable, message, args);
//    }

    /**
     * Formats the json content and print it
     *
     * @param json
     *            the json content
     */
    public static void json(String json) {
        Logger.json(json);
    }

    public static void json(List list) {
        Logger.json(JSON.toJSONString(list));
    }

    /**
     * Formats the json content and print it
     *
     * @param xml
     *            the xml content
     */
    public static void xml(String xml) {
        Logger.xml(xml);
    }




}
