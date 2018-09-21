package com.kanlulu.common.base;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import com.kanlulu.common.R;
import com.kanlulu.common.tools.ActivityManager;
import com.kanlulu.common.utils.BroadCastReceiverUtil;
import com.kanlulu.common.utils.DialogUtils;
import com.kanlulu.common.utils.LogUtils;
import com.kanlulu.common.utils.threepart.UmengUtils;


/**
 * 基activity，用于处理页面统一事件，复杂页面请用MVPBaseActivity
 * Created by lingxiaoming on 2017/7/21 0021.
 */

public abstract class DataBindingBaseActivity<B extends ViewDataBinding> extends FragmentActivity implements BroadCastReceiverUtil.OnReceiveBroadcast {
    private static final String TAG = "DataBindingBaseActivity";
    public B mViewBinding;
    public BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowTranslucentStatusBar(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityManager.getAppManager().addActivity(this);

        mViewBinding = DataBindingUtil.setContentView(this, getLayoutId());
        setFitSystemWindow();
        initBroadcast();
        initFirst();
    }

    private void setFitSystemWindow() {
        ViewGroup mContentView = (ViewGroup) getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            mChildView.setFitsSystemWindows(false);
        }
    }

    private void initBroadcast() {
        broadcastReceiver = BroadCastReceiverUtil.registerBroadcastReceiver(this, getBroadcastFilter(), this);
    }

    /**
     * 可在子类中复写该方法来实现监听的广播
     *
     * @return
     */
    public String[] getBroadcastFilter() {
        return new String[]{};
    }

    /**
     * 在子类中复写改方法来实现接收到广播
     *
     * @param context
     * @param intent
     */
    public void onBroadcastReceive(Context context, Intent intent) {
    }

    public void setTopTitle(boolean needBackIcon, String titleContent) {
        if (needBackIcon) findViewById(R.id.iv_back).setOnClickListener((View view) -> finish());
        else
            findViewById(R.id.iv_back).setVisibility(View.GONE);

        if (!TextUtils.isEmpty(titleContent)) {
            ((TextView) findViewById(R.id.tv_title)).setText(titleContent);
        }
    }

    protected View getBackIcon() {
        return findViewById(R.id.iv_back);
    }

    protected void setTopTitle(String titleContent, View.OnClickListener onClickListener) {
        findViewById(R.id.iv_back).setOnClickListener(onClickListener);

        if (!TextUtils.isEmpty(titleContent)) {
            ((TextView) findViewById(R.id.tv_title)).setText(titleContent);
        }
    }

    public void setTopTitle(boolean needBackIcon, String titleContent, String rightContent, View.OnClickListener onClickListener) {
        setTopTitle(needBackIcon, titleContent);
        if (!TextUtils.isEmpty(rightContent) && onClickListener != null) {
            TextView tvRight = (TextView) findViewById(R.id.tv_right);
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(rightContent);
            tvRight.setOnClickListener(onClickListener);
        }
    }

    public void setTopTitle(boolean needBackIcon, String titleContent, int right_icon, View.OnClickListener onClickListener) {
        setTopTitle(needBackIcon, titleContent);
        if (right_icon > 0 && onClickListener != null) {
            ImageView ivRight = (ImageView) findViewById(R.id.iv_right);
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setImageResource(right_icon);
            ivRight.setOnClickListener(onClickListener);
        }
    }

    public abstract int getLayoutId();

    public abstract void initFirst();

    @Override
    protected void onDestroy() {//solved dialog current , activity has leaked window DecorView
        super.onDestroy();
        if (broadcastReceiver != null) {
            BroadCastReceiverUtil.unRegisterBroadcastReceiver(this, broadcastReceiver);
        }
        try {
            Dialog dialog = DialogUtils.getCurrentDialog();
            if (dialog != null && dialog.getOwnerActivity() == this) {//别把别人的dialog给关掉了
                DialogUtils.dismiss();
            }
        } catch (Exception e) {
            LogUtils.e("Dialog", ActivityManager.getAppManager().currentActivity().toString() + "Dialog 关闭失败");
        }

        ActivityManager.getAppManager().removeActivity(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UmengUtils.onActivityResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        UmengUtils.onActivityPause(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        onBroadcastReceive(context, intent);
    }

}
