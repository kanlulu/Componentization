package com.kanlulu.common.network;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 19:18
 */
public class ApiException extends RuntimeException {
    public static final int ERROR_OTHER_LOGIN = 1049;//账号在其他地方登陆，被迫下线
    public static final int ERROR_UN_SAFE = 1053;//风控不通过，进入失败页面

    public int errorCode;

    public ApiException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
