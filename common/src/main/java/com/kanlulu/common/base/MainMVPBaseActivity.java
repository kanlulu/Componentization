package com.kanlulu.common.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.kanlulu.common.utils.UnRegisterSubscriber;
import java.util.ArrayList;

import rx.Subscription;

/**
 * MVP的Activity，注册和销毁view在这里，简单页面请继承用BaseActivity
 */

public abstract class MainMVPBaseActivity<P extends BasePresenter, B extends ViewDataBinding> extends MainBaseActivity<B> {
    protected P mPresenter;
    protected ArrayList<Subscription> subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        subscriptions = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        UnRegisterSubscriber.unRegisterSubscriber(subscriptions);
    }
}
