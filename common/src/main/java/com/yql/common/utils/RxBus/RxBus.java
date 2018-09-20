package com.yql.common.utils.RxBus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by kanlulu
 * DATE: 2018/4/19 10:01
 */
public class RxBus {
    private static volatile RxBus defaultInstance;
    private final Subject bus = new SerializedSubject(PublishSubject.create());

    public RxBus() {
    }

    public static RxBus getDefault() {
        RxBus rxBus = defaultInstance;
        if (defaultInstance == null) {
            Class var1 = RxBus.class;
            synchronized(RxBus.class) {
                rxBus = defaultInstance;
                if (defaultInstance == null) {
                    rxBus = new RxBus();
                    defaultInstance = rxBus;
                }
            }
        }

        return rxBus;
    }

    public void post(Object o) {
        this.bus.onNext(o);
    }

    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return this.bus.ofType(eventType);
    }
}
