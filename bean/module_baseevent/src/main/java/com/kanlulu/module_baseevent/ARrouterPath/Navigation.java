package com.kanlulu.module_baseevent.ARrouterPath;

import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by kanlulu
 * DATE: 2018/9/19 13:26
 */
public class Navigation {

    public static void goMainActivity() {
        ARouter.getInstance().build(NavigatorPath.MODULE_MAIN_ACTIVITY)
                .greenChannel()
                .navigation();
    }

    public static void goMainActivityWithClearTop() {
        ARouter.getInstance().build(NavigatorPath.MODULE_MAIN_ACTIVITY)
                .greenChannel()
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .navigation();
    }

    public static void goGuideActivity() {
        ARouter.getInstance().build(NavigatorPath.MODULE_MAIN_GUIDE)
                .greenChannel()
                .navigation();
    }

    public static void goLoginActivity() {
        ARouter.getInstance().build(NavigatorPath.MODULE_LOGIN_ACTIVITY)
                .greenChannel()
                .navigation();
    }

    public static void goCreditActivity() {
        ARouter.getInstance().build(NavigatorPath.MODULE_CREDIT_ACTIVITY)
                .greenChannel()
                .navigation();
    }
}
