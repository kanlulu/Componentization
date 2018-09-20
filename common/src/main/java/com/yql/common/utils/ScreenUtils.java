package com.yql.common.utils;

import android.content.Context;

/**
 * 屏幕工具类
 * Created by lingxiaoming on 2018-04-03.
 */

public class ScreenUtils {
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
