package com.yql.moduleMain.guide;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yql.common.base.DataBindingBaseSplashActivity;
import com.yql.common.utils.EventStatisticsUtils;
import com.yql.common.utils.PrefsUtils;
import com.yql.common.utils.WeakHandler;
import com.yql.moduleMain.R;
import com.yql.moduleMain.databinding.ActivitySplashBinding;
import com.yql.module_baseevent.ARrouterPath.Navigation;
import com.yql.module_baseevent.ARrouterPath.NavigatorPath;

/**
 * @author Lucius
 * @version V1.0
 * @Title 启动页
 * @Description
 * @date 2017/8/8 9:46
 */
public class SplashActivity extends DataBindingBaseSplashActivity<ActivitySplashBinding> {
    public static final int MSG_START = 1;
    public static final int MSG_FIRST = 2;
    public static final int MSG_LOGIN = 3;
    public static final int MSG_HSABEEN_LOGIN = 4;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //打开App提交 上次统计的数据
//        EventStatisticsUtils.getInstance().uploadEventToServer();

    }


    @Override
    public void initFirst() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        weakHandler.sendEmptyMessageDelayed(MSG_START, 2000);
    }

    WeakHandler weakHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START:
                    weakHandler.removeMessages(MSG_START);
                    if (PrefsUtils.getInstance().getBooleanByKey(PrefsUtils.IS_FIRST)) {//首次打开App
                        weakHandler.sendEmptyMessage(MSG_FIRST);
                    } else {
                        weakHandler.sendEmptyMessage(MSG_HSABEEN_LOGIN);//非第一次打开
                    }
                    break;
                case MSG_FIRST://首次打开应用
                    Navigation.goGuideActivity();
                    finish();
                    break;
                case MSG_LOGIN://一般不走这里，可用于测试
                    Navigation.goLoginActivity();
                    finish();
                    break;
                case MSG_HSABEEN_LOGIN://非首次打开应用
                    Navigation.goMainActivity();
                    finish();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
