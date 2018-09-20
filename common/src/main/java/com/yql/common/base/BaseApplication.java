package com.yql.common.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yql.common.BuildConfig;
import com.yql.jpush.JpushUtils;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 18:06
 */
public class BaseApplication extends Application {

    private static BaseApplication sInstance;

    public static BaseApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
//        testLogin();
        initARouter();
        initJpush();
    }

    private void initJpush() {
        JpushUtils.init(this, BuildConfig.DEBUG_ENABLE);
        JpushUtils.setAlias(sInstance, 1000, "18811746285");
    }

    private void initARouter() {
        if (BuildConfig.DEBUG_ENABLE) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(sInstance);
    }
}
