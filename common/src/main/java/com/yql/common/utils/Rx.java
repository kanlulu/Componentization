package com.yql.common.utils;

import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by kanlulu
 * DATE: 2018/4/18 9:34
 * 点击事件(放多次重复点击)
 */
public class Rx {
    public Rx() {
    }

    public static void click(View view, Action1<Void> action) {
        click(view, 500L, action);
    }

    public static void click(View view, long windowDuration, Action1<Void> action) {
        RxView.clicks(view).throttleFirst(windowDuration, TimeUnit.MILLISECONDS).subscribe(action);
    }

    public static void click(View view, Func1<Void, Boolean> predicate, Action1<Void> action) {
        click(view, 150L, predicate, action);
    }

    public static void click(View view, long windowDuration, Func1<Void, Boolean> predicate, Action1<Void> action) {
        RxView.clicks(view).throttleFirst(windowDuration, TimeUnit.MILLISECONDS).filter(predicate).subscribe(action);
    }

    public static void click(View view, Func1<Void, Boolean> predicate1, Func1<Void, Boolean> predicate2, Action1<Void> action) {
        click(view, 150L, predicate1, predicate2, action);
    }

    public static void click(View view, long windowDuration, Func1<Void, Boolean> predicate1, Func1<Void, Boolean> predicate2, Action1<Void> action) {
        RxView.clicks(view).throttleFirst(windowDuration, TimeUnit.MILLISECONDS).filter(predicate1).filter(predicate2).subscribe(action);
    }

    public static void click(View view, Func1<Void, Boolean> predicate, Action1<Boolean> action, Observable.Transformer<Object, Boolean> transformer) {
        click(view, 150L, predicate, action, transformer);
    }

    public static void click(View view, long windowDuration, Func1<Void, Boolean> predicate, Action1<Boolean> action, Observable.Transformer<Object, Boolean> transformer) {
        RxView.clicks(view).throttleFirst(windowDuration, TimeUnit.MILLISECONDS).filter(predicate).compose(transformer).subscribe(action);
    }

    public static void click(View view, Action1<Boolean> action, Observable.Transformer<Object, Boolean> transformer) {
        click(view, 150L, action, transformer);
    }

    public static void click(View view, long windowDuration, Action1<Boolean> action, Observable.Transformer<Object, Boolean> transformer) {
        RxView.clicks(view).throttleFirst(windowDuration, TimeUnit.MILLISECONDS).compose(transformer).subscribe(action);
    }

    public static void afterTextChange(TextView view, Action1<TextViewAfterTextChangeEvent> action) {
        afterTextChange(view, 150L, action);
    }

    public static void afterTextChange(TextView view, long windowDuration, Action1<TextViewAfterTextChangeEvent> action) {
        RxTextView.afterTextChangeEvents(view).debounce(windowDuration, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(action);
    }

    public static void longClick(View view, Action1<Void> action) {
        longClick(view, 150L, action);
    }

    public static void longClick(View view, long windowDuration, Action1<Void> action) {
        RxView.longClicks(view).throttleFirst(windowDuration, TimeUnit.MILLISECONDS).subscribe(action);
    }

    public static void longClick(View view, Func1<Void, Boolean> predicate, Action1<Void> action) {
        longClick(view, 150L, predicate, action);
    }

    public static void longClick(View view, long windowDuration, Func1<Void, Boolean> predicate, Action1<Void> action) {
        RxView.longClicks(view).throttleFirst(windowDuration, TimeUnit.MILLISECONDS).filter(predicate).subscribe(action);
    }

    public static void longClick(View view, Func1<Void, Boolean> predicate1, Func1<Void, Boolean> predicate2, Action1<Void> action) {
        longClick(view, 150L, predicate1, predicate2, action);
    }

    public static void longClick(View view, long windowDuration, Func1<Void, Boolean> predicate1, Func1<Void, Boolean> predicate2, Action1<Void> action) {
        RxView.longClicks(view).throttleFirst(windowDuration, TimeUnit.MILLISECONDS).filter(predicate1).filter(predicate2).subscribe(action);
    }
}
