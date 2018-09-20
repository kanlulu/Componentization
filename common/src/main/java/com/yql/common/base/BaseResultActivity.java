package com.yql.common.base;

import android.os.CountDownTimer;
import android.view.View;

import com.yql.common.R;
import com.yql.common.databinding.ActivityResultBinding;


/**
 * 有倒计时的结果页，抽象类
 * Created by lingxiaoming on 2017/8/4 0004.
 */

public abstract class BaseResultActivity extends DataBindingBaseActivity<ActivityResultBinding> {
    private CountDownTimer countDownTimer;//倒计时


    @Override
    public int getLayoutId() {
        return R.layout.activity_result;
    }


    @Override
    public void initFirst() {
        setTopTitle(true, getResultTitle(), "完成", new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                countDownFinish();
            }
        });
        mViewBinding.ivIcon.setImageResource(getResultIcon());
        mViewBinding.tvContent.setText(getResultContent());
        initCountDownTime();
    }

    private void initCountDownTime() {
        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mViewBinding.tvJump.setText(millisUntilFinished/1000+""+getCountDownText());
            }

            @Override
            public void onFinish() {
                countDownFinish();
            }
        };
        countDownTimer.start();
    }

    public abstract String getResultTitle();//结果页title

    public abstract int getResultIcon();//结果页icon的resource id

    public abstract String getResultContent();//一级内容

    public abstract String getCountDownText();//3秒倒计时文字，不包括3

    public abstract void countDownFinish();//x秒倒计时后事件
}
