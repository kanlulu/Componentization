package com.kanlulu.common.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtils {

    /**
     * 时、分、秒
     *
     * @param timeLengh 单位毫秒
     * @return
     */
    public static String transToHHMMSS(long timeLengh) {
        if (timeLengh < 0) return "00时00分00秒";
        long timeSecend = timeLengh / 1000;
        int h = (int) timeSecend / 3600;
        int m = (int) (timeSecend % 3600 / 60);
        int s = (int) (timeSecend % 3600 % 60);
        return String.format("%02d时%02d分%02d秒", h, m, s);
    }

    /**
     * 天、时、分、秒
     *
     * @param timeLengh 单位毫秒
     * @return
     */
    public static String transToDDHHMMSS(long timeLengh) {
        if (timeLengh < 0) return "00时00分00秒";
        long timeSecend = timeLengh / 1000;
        int day = (int) timeSecend / (3600 * 24);
        int h = (int) (timeSecend % (3600 * 24) / 3600);
        int m = (int) (timeSecend % 3600 / 60);
        int s = (int) (timeSecend % 60);
        return String.format("%02d天%02d时%02d分%02d秒", day, h, m, s);
    }

    /**
     * 获取天数
     *
     * @param timeLengh 单位毫秒
     * @return
     */
    public static String transToDD(long timeLengh) {
        if (timeLengh < 0) return "00";
        long timeSecend = timeLengh / 1000;
        int h = (int) timeSecend / (3600 * 24);
        return String.format("%02d", h);
    }

    /**
     * 获取小时（不会超过24）
     *
     * @param timeLengh 单位毫秒
     * @return
     */
    public static String transToHH(long timeLengh) {
        if (timeLengh < 0) return "00";
        long timeSecend = timeLengh / 1000;
        int h = (int) (timeSecend % (3600 * 24)) / 3600;
        return String.format("%02d", h);
    }

    /**
     * 获取分钟
     *
     * @param timeLengh 单位毫秒
     * @return
     */
    public static String transToMM(long timeLengh) {
        if (timeLengh < 0) return "00";
        long timeSecend = timeLengh / 1000;
        int m = (int) (timeSecend % 3600 / 60);
        return String.format("%02d", m);
    }

    /**
     * 获取秒
     *
     * @param timeLengh 单位毫秒
     * @return
     */
    public static String transToSS(long timeLengh) {
        if (timeLengh < 0) return "00";
        long timeSecend = timeLengh / 1000;
        int s = (int) (timeSecend % 60);
        return String.format("%02d", s);
    }

    public static String getYYMMDDHHmmss(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//yyyy-MM-dd
        Date date = new Date(time);
        return format.format(date);
    }

    public static String getYYMMDDHHmm(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");//yyyy-MM-dd
        Date date = new Date(time);
        return format.format(date);
    }

    public static String getMMDDHHmm(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");//yyyy-MM-dd
        Date date = new Date(time);
        return format.format(date);
    }

    public static String getHHmm(long time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(time);
    }

    public static String getYYMMDD(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(time);
    }

    public static String getYYMMDDPoint(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        return df.format(time);
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(long date1, long date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date(date1));
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date(date2));
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 < year2) {//不同年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        }else if (year1 > year2){
            int timeDistance = 0;
            for (int i = year2; i < year1; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return -timeDistance + (day2 - day1);
        } else {//同一年
            return day2 - day1;
        }
    }
}
