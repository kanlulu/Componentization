package com.kanlulu.common.base;


import com.kanlulu.common.utils.DialogUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by kanlulu
 * DATE: 2018/4/24 14:16
 */
public class LoadingObserver<T> extends MyObserver<T> {
    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        DialogUtils.showProgressDialog("加载中");
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
        DialogUtils.dismiss();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        DialogUtils.dismiss();
    }
}
