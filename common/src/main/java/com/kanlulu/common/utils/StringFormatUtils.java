package com.kanlulu.common.utils;

public class StringFormatUtils {

    /**
     * 去除所有的空格
     * @param str
     * @return
     */
    public static String removeAllSpace(String str) {
        String tmpstr=str.replace(" ","");
        return tmpstr;
    }

    /**
     * 数字转汉字数字
     * @param str
     * @return
     */
    public static String toChinese(int str) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";
        int n = (str + "").length();
        for (int i = 0; i < n; i++) {

            int num = (str + "").charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }
        return result;
    }
}
