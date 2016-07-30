package com.mangues.lifecircleapp.bean;

/**
 * Created by mangues on 16/7/27.
 */

public class MessageEvent {
    public static Integer NOLOGIN = 010000;



    private int code;
    private String message;

    public MessageEvent(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
