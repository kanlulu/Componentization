package com.kanlulu.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kanlulu
 * DATE: 2018/5/28 11:20
 */
public class AgeUtils {
    public static boolean getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);
            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                int birthYear = birth.get(Calendar.YEAR);//出生年份
                int nowYear = now.get(Calendar.YEAR);//当前年份
                age = nowYear - birthYear - 1;
                //通过月日判断具体年龄
                if (birthYear % 4 == 0 && nowYear % 4 != 0) {//出生年是闰年，当前年是平年
                    if (now.get(Calendar.DAY_OF_YEAR) <= 59) {//当前日期在0228之前
                        if (now.get(Calendar.DAY_OF_YEAR) >= birth.get(Calendar.DAY_OF_YEAR)) {
                            age += 1;
                        }
                    } else {//0228之后 全年少一天
                        if (now.get(Calendar.DAY_OF_YEAR) + 1 >= birth.get(Calendar.DAY_OF_YEAR)) {
                            age += 1;
                        }
                    }
                } else if (birthYear % 4 != 0 && nowYear % 4 == 0) {//出生年是平年，当前年是闰年
                    if (now.get(Calendar.DAY_OF_YEAR) <= 59) {//0228之前
                        if (now.get(Calendar.DAY_OF_YEAR) >= birth.get(Calendar.DAY_OF_YEAR)) {
                            age += 1;
                        }
                    } else {//0229之后 全年少一天
                        if (now.get(Calendar.DAY_OF_YEAR) >= birth.get(Calendar.DAY_OF_YEAR) + 1) {
                            age += 1;
                        }
                    }
                } else {//出生年和当前年都是平年或闰年(不考虑1900年和2100年)
                    if (now.get(Calendar.DAY_OF_YEAR) >= birth.get(Calendar.DAY_OF_YEAR)) {
                        age += 1;
                    }
                }
            }
            return age >= 18;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return false;
        }
    }
}
