package com.kanlulu.common.utils;

import java.util.ArrayList;

import rx.Subscription;

public class UnRegisterSubscriber {
    public static void unRegisterSubscriber(ArrayList<Subscription> subscriptions){
        if (subscriptions==null||subscriptions.size()<=0) {
            return;
        }

        for (Subscription subscriber :subscriptions) {
            if (subscriber!=null) {
                subscriber.unsubscribe();
            }
        }
    }

}
