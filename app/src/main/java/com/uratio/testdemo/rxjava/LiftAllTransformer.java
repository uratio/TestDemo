package com.uratio.testdemo.rxjava;

import rx.Observable;

public class LiftAllTransformer implements Observable.Transformer<String, Integer> {
    @Override
    public Observable<Integer> call(Observable<String> stringObservable) {
        return null;
    }
}
