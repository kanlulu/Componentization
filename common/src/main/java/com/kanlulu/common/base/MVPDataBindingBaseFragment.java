package com.kanlulu.common.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.kanlulu.common.utils.UnRegisterSubscriber;

import java.util.ArrayList;

import rx.Subscription;

/**
 * MVP的Fragment，注册和销毁view在这里，简单页面请继承用BaseFragment
 * Created by lingxiaoming on 2017/7/21 0021.
 */

public abstract class MVPDataBindingBaseFragment<P extends BasePresenter, B extends ViewDataBinding> extends DataBindingBaseFragment<B> {
    protected P mPresenter;
    protected ArrayList<Subscription> subscriptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        subscriptions=new ArrayList<>();
    }

    protected abstract P createPresenter();


    @Override
    public void onDetach() {
        super.onDetach();
        UnRegisterSubscriber.unRegisterSubscriber(subscriptions);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
