package com.uratio.testdemo.designmodel.observer;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * @author lang
 * @data 2021/3/2
 */
public class PersonObserver implements Observer {

    private String name;

    public PersonObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.i(PersonObserver.class.getSimpleName(), name + " 接收到通知啦 "+ arg);
    }
}
