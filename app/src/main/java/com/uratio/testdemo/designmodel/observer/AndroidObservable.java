package com.uratio.testdemo.designmodel.observer;

import java.util.Observable;

/**
 * @author lang
 * @data 2021/3/2
 */
public class AndroidObservable extends Observable {

    public void postNewVersion(String version) {
        setChanged();
        notifyObservers(version);
    }

}
