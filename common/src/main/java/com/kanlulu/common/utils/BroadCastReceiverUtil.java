package com.kanlulu.common.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;


/**
 * 注册广播，发送广播，注销广播
 */
public class BroadCastReceiverUtil {
    private static LocalBroadcastManager mLocalBroadcastManager;

    private static void getLocalBroadcastManager(Context context) {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    public interface OnReceiveBroadcast {
        public void onReceive(Context context, Intent intent);
    }

    private static class CustomBroadcastReceiver extends BroadcastReceiver {
        private OnReceiveBroadcast onReceiveBroadcast;

        public CustomBroadcastReceiver(OnReceiveBroadcast onReceiveBroadcast) {
            this.onReceiveBroadcast = onReceiveBroadcast;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (onReceiveBroadcast != null) {
                onReceiveBroadcast.onReceive(context, intent);
            }
        }
    }

    public static BroadcastReceiver registerBroadcastReceiver(Context context,
                                                              String[] filters, OnReceiveBroadcast onReceiveBroadcast) {
        BroadcastReceiver broadcastReceiver = new CustomBroadcastReceiver(
                onReceiveBroadcast);
        IntentFilter filter = new IntentFilter();
        for (String filterStr : filters) {
            filter.addAction(filterStr);
        }
        if (mLocalBroadcastManager == null) {
            getLocalBroadcastManager(context);
        }
        mLocalBroadcastManager.registerReceiver(broadcastReceiver, filter);
        return broadcastReceiver;
    }

    public static void unRegisterBroadcastReceiver(Context context,
                                                   BroadcastReceiver broadcastReceiver) {
        if (context != null && broadcastReceiver != null) {
            if (mLocalBroadcastManager == null) {
                getLocalBroadcastManager(context.getApplicationContext());
            }
            mLocalBroadcastManager.unregisterReceiver(broadcastReceiver);
        }
    }

}
