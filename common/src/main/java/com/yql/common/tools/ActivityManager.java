package com.yql.common.tools;

import android.app.Activity;
import android.content.Context;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 21:38
 */
public class ActivityManager {
    private static LinkedList<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
    }

    /**
     * 单一实例
     */
    public static ActivityManager getAppManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new LinkedList<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 移除Activity
     */
    public void removeActivity(Activity activity) {
        if (activityStack != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 获取当前Activity(堆栈中最后一个压入的)
     */
    public Activity currentActivity() {
        try {
            Activity activity = activityStack.getLast();
            return activity;
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 结束当前Activity(堆栈中最后一个压入的)
     */
    public void finishActivity() {
        Activity activity = activityStack.getLast();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            if (activityStack != null) {
                activityStack.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    public int ActivityStackSize() {
        return activityStack == null ? 0 : activityStack.size();
    }
}
