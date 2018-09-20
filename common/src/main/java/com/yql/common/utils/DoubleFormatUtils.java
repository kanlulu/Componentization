package com.yql.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 格式化double数据，如：保留两位有效小数
 * Created by lingxiaoming on 2017/9/21 0021.
 */

public class DoubleFormatUtils {
    /**
     * double数据格式化小数点
     *
     * @param input      输入double值
     * @param precision  精度，小数位数
     * @param isHalfDown 是否四舍五入，false:直接截取，true:四舍五入
     * @return 格式化后double数据
     */
    private static double formatDouble(double input, int precision, boolean isHalfDown) {
        BigDecimal b = new BigDecimal(input);
        int type = BigDecimal.ROUND_HALF_UP;
        if (!isHalfDown) {
            type = BigDecimal.ROUND_DOWN;
        }
        double value = b.setScale(precision, type).doubleValue();
        return value;
    }

    /**
     * double数据格式化2位小数点
     * @param input
     * @return
     */
    private static double formatDouble(double input) {
        return formatDouble(input, 2, true);
    }

    /**
     * 保留两位，不足取0
     * @param input
     * @return
     */
    public static String formatDoubleToString(double input){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(input);
    }

//    /**
//     * 保留两位，不足舍去
//     * @param input
//     * @return
//     */
//    public static String formatDoubleToString2(double input){//小数点前必须有值，小数点后可以忽略(二者都表示占位，0表示必须有值，无值时用0补位，#表示无值时忽略)
//        DecimalFormat df = new DecimalFormat("0.##");
//        return df.format(input);
//    }
}
