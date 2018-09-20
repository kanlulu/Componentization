package com.yql.common.utils;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lucius
 * @version V1.0
 * @Title 验证类
 * @Package com.hyd.huayingbao.tools.check
 * @Description
 * @date 2016/7/27 10:28
 */
public class CheckUtils {


    /**
     *  判断当前号码是否为手机号
     *
     * @param phone
     * @return
     */
    public static boolean isMobileNo(String phone){
        String string = "^(1)\\d{10}$";
        Pattern p = Pattern.compile(string);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * @param checkType 校验类型：0校验手机号码，1校验座机号码，2两者都校验满足其一就可
     * @param phoneNum
     * */
    public static boolean validPhoneNum(String checkType,String phoneNum){
        boolean flag=false;
        Pattern p1 = null;
        Pattern p2 = null;
        Matcher m = null;
        p1 = Pattern.compile("^(1)\\d{10}$");
        p2 = Pattern.compile("^(0[0-9]{2,3}\\-)?([1-9][0-9]{6,7})$");
        if("0".equals(checkType)){
            System.out.println(phoneNum.length());
            if(phoneNum.length()!=11){
                return false;
            }else{
                m = p1.matcher(phoneNum);
                flag = m.matches();
            }
        }else if("1".equals(checkType)){
            if(phoneNum.length()<11||phoneNum.length()>=16){
                return false;
            }else{
                m = p2.matcher(phoneNum);
                flag = m.matches();
            }
        }else if("2".equals(checkType)){
            if(!((phoneNum.length() == 11 && p1.matcher(phoneNum).matches())||(phoneNum.length()<16&&p2.matcher(phoneNum).matches()))){
                return false;
            }else{
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 前后密码输入是否一致
     *
     * @param pwd
     * @param rePwd
     * @return
     */
    public static boolean checkPwd(String pwd, String rePwd){
        if(TextUtils.isEmpty(pwd)){
//            Toast.makeText(HYBApplication.getInstance().getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(rePwd)){
//            Toast.makeText(HYBApplication.getInstance().getApplicationContext(), "请确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }else if(pwd.length()<6){
//            Toast.makeText(HYBApplication.getInstance().getApplicationContext(), "密码长度少于6位", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!pwd.equals(rePwd)){
//            Toast.makeText(HYBApplication.getInstance().getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /*
     * 判断是否为浮点数，包括double和float
     *
     * @param str 传入的字符串
     *
     * @return 是浮点数返回true,否则返回false
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("[0-9]+\\.?[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证身份证
     *
     * @param value
     * @return
     */
    public static boolean checkIdcard(String value) {
        int length = 0;
        if (value == null) {
            return false;
        } else {
            length = value.length();


            if (!(length == 15 || length == 18)) {
                return false;
            }
        }
        String[] areasArray = { "11", "12", "13", "14", "15", "21", "22", "23", "31",
                "32", "33", "34", "35", "36", "37", "41", "42", "43", "44",
                "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
                "64", "65", "71", "81", "82", "91" };


        HashSet< String > areasSet = new HashSet< String >(Arrays.asList(areasArray));
        String valueStart2 = value.substring(0, 2);


        if (areasSet.contains(valueStart2)) {} else {
            return false;
        }

        Pattern pattern = null;
        Matcher matcher = null;


        int year = 0;
        switch (length) {
            case 15:
                try{
                    year = Integer.parseInt(value.substring(6, 8)) + 1900;
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    return false;
                }

                if (year % 4 == 0 || (year % 100 == 0 && year % 4 == 0)) {
                    pattern = Pattern.compile("^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$"); // 测试出生日期的合法性
                } else {
                    pattern = Pattern.compile("^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$"); // 测试出生日期的合法性
                }
                matcher = pattern.matcher(value);
                if (matcher.find()) {
                    return true;
                } else {
                    return false;
                }
            case 18:
                try{
                    year = Integer.parseInt(value.substring(6, 10));
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    return false;
                }
                //[1-2][0-9]

                if (year % 4 == 0 || (year % 100 == 0 && year % 4 == 0)) {
                    pattern = Pattern.compile("^[1-9][0-9]{5}[1-2][0-9][0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$"); // 测试出生日期的合法性
                } else {
                    pattern = Pattern.compile("^[1-9][0-9]{5}[1-2][0-9][0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$"); // 测试出生日期的合法性
                }


                matcher = pattern.matcher(value);
                if (matcher.find()) {
                    int S = (Integer.parseInt(value.substring(0, 1)) + Integer.parseInt(value.substring(10, 11))) * 7 + (Integer.parseInt(value.substring(1, 2)) + Integer.parseInt(value.substring(11, 12))) * 9 + (Integer.parseInt(value.substring(2, 3)) + Integer.parseInt(value.substring(12, 13))) * 10 + (Integer.parseInt(value.substring(3, 4)) + Integer.parseInt(value.substring(13, 14))) * 5 + (Integer.parseInt(value.substring(4, 5)) + Integer.parseInt(value.substring(14, 15))) * 8 + (Integer.parseInt(value.substring(5, 6)) + Integer.parseInt(value.substring(15, 16))) * 4 + (Integer.parseInt(value.substring(6, 7)) + Integer.parseInt(value.substring(16, 17))) * 2 + Integer.parseInt(value.substring(7, 8)) * 1 + Integer.parseInt(value.substring(8, 9)) * 6 + Integer.parseInt(value.substring(9, 10)) * 3;
                    int Y = S % 11;
                    String M = "F";
                    String JYM = "10X98765432";
                    M = JYM.substring(Y, Y + 1); // 判断校验位
                    if (M.equals(value.substring(17, 18))) {
                        return true; // 检测ID的校验位
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            default:
                return false;
        }
    }
}