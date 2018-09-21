package com.kanlulu.common.utils.RxBus;

import rx.Subscriber;
import rx.observers.SerializedObserver;
import rx.subjects.Subject;

/**
 * Created by kanlulu
 * DATE: 2018/4/19 10:01
 */
public class SerializedSubject<T, R> extends Subject<T, R> {
    private final SerializedObserver<T> observer;
    private final Subject<T, R> actual;

    public SerializedSubject(final Subject<T, R> actual) {
        super(new OnSubscribe<R>() {

            @Override
            public void call(Subscriber<? super R> child) {
                actual.unsafeSubscribe(child);
            }

        });
        this.actual = actual;
        this.observer = new SerializedObserver<T>(actual);
    }

    @Override
    public void onCompleted() {
        observer.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        observer.onError(e);
    }

    @Override
    public void onNext(T t) {
        observer.onNext(t);
    }

    @Override
    public boolean hasObservers() {
        return actual.hasObservers();
    }
}
