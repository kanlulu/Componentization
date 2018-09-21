package com.kanlulu.common.base;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by kanlulu
 * DATE: 2018/4/13 15:58
 */
public class DensityUtils {
    private DensityUtils()

    {

        /* cannot be instantiated */

        throw new UnsupportedOperationException("cannot be instantiated");

    }


    /**
     * dp转px
     *
     * @param context
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * dp转px
     *
     * @param
     * @return
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, BaseApplication.getInstance().getResources().getDisplayMetrics());
    }

    public static float dp2pxFloat(Context context, float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }


    /**
     * sp转px
     *
     * @param context
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }


    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 字符串中，中文的字数
     *
     * @param str
     * @return
     */
    public static int getChineseNums(String str) {
        int byteLength = str.getBytes().length;
        int strLength = str.length();
        return (byteLength - strLength) / 2;
    }

    /**
     * 字符串中，非中文的字数
     *
     * @param str
     * @return
     */
    public static int getNoChineseNums(String str) {
        int byteLength = str.getBytes().length;
        int strLength = str.length();
        return strLength - (byteLength - strLength) / 2;
    }
}
