package com.yql.common.utils;

import android.os.Build;
import android.provider.Settings;

import com.yql.common.base.BaseApplication;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 21:55
 * 获取设备信息 deviceId
 */
public class DeviceUtils {
    /**
     * 获取设备类型
     *
     * @return
     */
    public static String getDeviceId() {
        return Settings.Secure.getString(BaseApplication.getInstance().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getMobileInfo(){
        return Build.MODEL+":"+Build.VERSION.RELEASE;
    }
}
