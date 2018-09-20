package com.yql.common.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        if ("cn.jpush.android.intent.NOTIFICATION_RECEIVED".equals(intent.getAction())) {//接收通知
            Toast.makeText(context, "接收到消息", Toast.LENGTH_SHORT).show();

        } else if ("cn.jpush.android.intent.NOTIFICATION_OPENED".equals(intent.getAction())) {//点开通知
            Toast.makeText(context, "点开了消息", Toast.LENGTH_SHORT).show();
            String result = bundle.getString("cn.jpush.android.EXTRA");
            ARouter.getInstance().build("/push/PushAgentActivity")
                    .withString("result", result)
                    .greenChannel()
                    .navigation();
        }
    }
}
