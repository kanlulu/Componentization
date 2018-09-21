package com.kanlulu.common.push;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kanlulu.common.utils.LogUtils;

/**
 * Created by kanlulu
 * DATE: 2018/4/17 13:10
 */
@Route(path = "/push/PushAgentActivity")
public class PushAgentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String result = getIntent().getStringExtra("result");
        LogUtils.e("pushResult", result);
    }
}
