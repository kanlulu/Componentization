package com.kanlulu.common.base;


import java.lang.ref.WeakReference;

/**
 * Presenter基类
 */

public class BasePresenter<V> {
    public WeakReference<V> mvpViewWeakReference;

    public void attachView(V mvpView) {
        this.mvpViewWeakReference = new WeakReference<>(mvpView);
    }

    public void detachView() {
        if (mvpViewWeakReference != null) {
            mvpViewWeakReference.clear();
            this.mvpViewWeakReference = null;
        }
    }

    protected V getView() {
        return mvpViewWeakReference == null ? null : mvpViewWeakReference.get();
    }


}
