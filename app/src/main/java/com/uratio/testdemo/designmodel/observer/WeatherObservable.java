package com.uratio.testdemo.designmodel.observer;

import java.util.ArrayList;

/**
 * @author lang
 * @data 2021/3/2
 */
public class WeatherObservable extends WeatherObserver {
    private ArrayList<WeatherObserver> list = new ArrayList<>();

    public void add(WeatherObserver observerListener) {
        list.add(observerListener);
    }

    public void remove(WeatherObserver observerListener) {
        if (list.contains(observerListener)) {
            list.remove(observerListener);
        }
    }

    @Override
    public void typhoon() {
        for (WeatherObserver observerListener : list) {
            observerListener.typhoon();
        }
    }

    @Override
    public void sun() {
        for (WeatherObserver observerListener : list) {
            observerListener.sun();
        }
    }

    @Override
    public void rain() {
        for (WeatherObserver observerListener : list) {
            observerListener.rain();
        }
    }
}
