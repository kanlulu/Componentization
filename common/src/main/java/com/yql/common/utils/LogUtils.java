package com.yql.common.utils;

import android.util.Log;

import com.yql.common.network.ApiConstants;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 20:38
 */
public class LogUtils {
    public static void i(String tag, String content){
        if(ApiConstants.DEBUG) Log.i(tag, content);
    }

    public static void d(String tag, String content){
        if(ApiConstants.DEBUG) Log.d(tag, content);
    }

    public static void e(String tag, String content){
        if(ApiConstants.DEBUG) Log.e(tag, content);
    }

    public static void v(String tag, String content){
        if(ApiConstants.DEBUG) Log.v(tag, content);
    }

    public static void w(String tag, String content){
        if(ApiConstants.DEBUG) Log.w(tag, content);
    }
}
