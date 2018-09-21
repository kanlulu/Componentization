package com.kanlulu.common.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.kanlulu.common.base.BaseApplication;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 20:42
 */
public class AppVersionUtils {
    public static int getVersionCode() {
        try {
            PackageManager pm = BaseApplication.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName() {
        try {
            PackageManager pm = BaseApplication.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
