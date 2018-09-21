package com.kanlulu.common.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

/**
 * MVP的Activity，注册和销毁view在这里，简单页面请继承用BaseActivity
 */

public abstract class MVPDataBindingBaseListActivity<B extends ViewDataBinding> extends DataBindingBaseActivity<B> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
