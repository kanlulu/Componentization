package com.kanlulu.common.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanlulu.common.utils.threepart.UmengUtils;


/**
 * 基Fragment，用于处理页面统一事件，复杂页面请用MVPBaseFragment
 */

public abstract class DataBindingBaseFragment<B extends ViewDataBinding> extends Fragment {
    private static final String TAG = "DataBindingBaseFragment";
    public B mViewBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return mViewBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFirst();
    }

    public abstract int getLayoutId();

    public abstract void initFirst();

    @Override
    public void onResume() {
        super.onResume();
        UmengUtils.onFragmentResume(this);//统计页面
    }

    @Override
    public void onPause() {
//        LogUtils.d(TAG, "onPause "+this.getClass().getSimpleName());
        super.onPause();
        UmengUtils.onFragmentPause(this);
    }

    @Override
    public void onAttach(Context context) {
//        LogUtils.d(TAG, "onAttach "+this.getClass().getSimpleName());
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
//        LogUtils.d(TAG, "onDetach "+this.getClass().getSimpleName());
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        LogUtils.d(TAG, "onCreate "+this.getClass().getSimpleName());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
//        LogUtils.d(TAG, "onStop "+this.getClass().getSimpleName());
        super.onStop();
    }

    @Override
    public void onDestroy() {
//        LogUtils.d(TAG, "onDestroy "+this.getClass().getSimpleName());
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//        LogUtils.d(TAG, "onActivityCreated "+this.getClass().getSimpleName());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
//        LogUtils.d(TAG, "onActivityCreated "+this.getClass().getSimpleName());
        super.onDestroyView();
    }
}
