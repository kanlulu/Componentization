package com.kanlulu.common.base;


import com.kanlulu.common.network.ApiException;
import com.kanlulu.common.tools.CommonDataManager;
import com.kanlulu.common.utils.LogUtils;
import com.kanlulu.common.utils.ToastUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by kanlulu
 * DATE: 2018/9/1 21:42
 */
public abstract class MyObserver<T> implements Observer<T> {
    private static final String TAG = "MyObserver";


    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        LogUtils.d(TAG, "onNext: " + t.toString());
    }

    @Override
    public void onError(@NonNull Throwable e) {
        LogUtils.e(TAG, "onError: " + e.getMessage() + Thread.currentThread().getName());
        String errorMessage = e.getMessage();
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            LogUtils.e(TAG, "ApiException onError: " + apiException.errorCode + " " + e.getMessage());
            if (apiException.errorCode == ApiException.ERROR_OTHER_LOGIN) {//下线通知
                CommonDataManager.setUser(null);
                CommonDataManager.setStatistics(null);
//                if (!Constants.IS_LOGINOUT) {
                //账号被顶替 弹窗提示
//                    MainActivity.goAndShowOtherLoginDialog(ActivityManager.getAppManager().currentActivity(), errorMessage);
//                }
                return;
            } else if (apiException.errorCode == ApiException.ERROR_UN_SAFE) {//风控失败
                onOtherError(ApiException.ERROR_UN_SAFE);
            }
        } else if (e instanceof HttpException) {
            errorMessage = "服务器内部错误";
        } else if (e instanceof IOException) {
            errorMessage = "您的网络不给力<br>请稍后再试";
        }

        try {
            if (errorMessage.contains("borrow_order_no=") || errorMessage.contains("该银行卡暂不支持")) {
                return;//不提示出来
            }
            if (errorMessage.contains("您的网络不给力"))
                ToastUtils.showSquare(errorMessage);
            else
                ToastUtils.showText(errorMessage);
        } catch (Exception e1) {//防止不在主线程toast闪退
            e1.getMessage();
        }
    }

    public void onOtherError(int error_code) {

    }
}
