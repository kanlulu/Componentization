package com.kanlulu.common.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import java.io.Serializable;

/**
 * 注册广播，发送广播，注销广播
 */
public class BroadCastReceiverUtil {
    public static final String BROADCAST_LOGIN_SUCCESS = "action_login_success";//登录成功广播

    public static final String BROADCAST_LL_H5_FAILURE = "action_ll_h5_failure";//连连网页失败
    public static final String BROADCAST_BINDCARD_H5_SUCCESS = "action_bindcard_h5_success";//网页绑卡成功
    public static final String BROADCAST_BINDCARD_SUCCESS = "action_bindcard_success";//绑卡成功
    public static final String BROADCAST_REPAYMENT_SUCCESS = "action_repayment_success";//还款成功
    public static final String BROADCAST_EXTENSION_SUCCESS = "action_extension_success";//延期成功
    public static final String BROADCAST_REPAYMENT_H5_SUCCESS = "action_repayment_h5_success";//网页还款成功
    public static final String BROADCAST_EXTENSION_H5_SUCCESS = "action_extension_h5_success";//网页支付延期费用成功

    public static final String BROADCAST_CREDIT_APPLY_SUCCESS = "action_credit_apply_success";//额度审核成功
    public static final String BROADCAST_ESIGN_SUCCESS = "action_esign_success";//借款电子签约成功
    public static final String BROADCAST_BORROW_MONEY_SUCCESS = "action_borrow_money_success";//查询放款成功
    public static final String BROADCAST_BORROW_MONEY_FINISH = "action_borrow_money_finish";//查询放款结果失败或者处理中，需关闭中间页面如借款确认页


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

    public static void sendBroadcast(Context context, String filter) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(filter);
        if (mLocalBroadcastManager == null) {
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter, Bundle bundle) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(filter);
        intent.putExtras(bundle);
        if (mLocalBroadcastManager == null) {
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter,
                                     String bundleName, Bundle bundle) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(filter);
        intent.putExtra(bundleName, bundle);
        if (mLocalBroadcastManager == null) {
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter, String name, Serializable serializable) {
        Intent intent = new Intent();
        intent.putExtra(name, serializable);
        intent.setAction(filter);
        if (mLocalBroadcastManager == null) {
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter, String name, String value) {
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setAction(filter);
        if (mLocalBroadcastManager == null) {
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter, String name, long value) {
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setAction(filter);
        if (mLocalBroadcastManager == null) {
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter, String name, int value) {
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setAction(filter);
        if (mLocalBroadcastManager == null) {
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String filter, String name, String value, String name1, String value1) {
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.putExtra(name1, value1);
        intent.setAction(filter);
        if (mLocalBroadcastManager == null) {
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, Intent intent) {
        if (mLocalBroadcastManager == null) {
            getLocalBroadcastManager(context.getApplicationContext());
        }
        mLocalBroadcastManager.sendBroadcast(intent);
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
