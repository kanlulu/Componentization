package com.kanlulu.jpush;

import android.content.Context;
import android.text.TextUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by kanlulu
 * DATE: 2018/9/18 16:57
 */
public class JpushUtils {
    public static void init(Context context, boolean isDebug) {
        JPushInterface.setDebugMode(isDebug);
        JPushInterface.init(context);
    }

    public static void setAlias(Context context, int aliasCode, String mobile) {
        if (!TextUtils.isEmpty(mobile)) {
            JPushInterface.setAlias(context, aliasCode, mobile);
        } else {
            deleteAlias(context, aliasCode);
        }
    }

    public static void deleteAlias(Context context, int aliasCode) {
        JPushInterface.deleteAlias(context, aliasCode);
    }
}
