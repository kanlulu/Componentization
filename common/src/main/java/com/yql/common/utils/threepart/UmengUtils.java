package com.yql.common.utils.threepart;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * 友盟工具类
 * Created by lingxiaoming on 2017/8/7 0007.
 */

public class UmengUtils {

    public static void onActivityResume(Activity activity){
        MobclickAgent.onResume(activity);
    }
    public static void onActivityPause(Activity activity){
        MobclickAgent.onPause(activity);
    }

    public static void onFragmentResume(Fragment fragment){
        MobclickAgent.onPageStart(fragment.getClass().getSimpleName());
    }
    public static void onFragmentPause(Fragment fragment){
        MobclickAgent.onPageEnd(fragment.getClass().getSimpleName());
    }

    /**
     * 计数统计
     * @param context
     * @param eventId
     */
    public static void onEvent(Context context, String eventId){
        MobclickAgent.onEvent(context, eventId);
    }

    /**
     * 计数统计加属性区别
     * @param context
     * @param eventId
     */
    public static void onEvent2(Context context, String eventId, HashMap map){
        MobclickAgent.onEvent(context, eventId, map);
    }

}
