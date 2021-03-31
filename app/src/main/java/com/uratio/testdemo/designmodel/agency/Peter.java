package com.uratio.testdemo.designmodel.agency;

import android.util.Log;

/**
 * @author lang
 * @data 2021/3/2
 */
public class Peter implements ProxyInterface {
    private static final String TAG = Peter.class.getSimpleName();

    @Override
    public void choiceBetterHouse() {
        Log.e(TAG, "挑选优质房子");
    }

    @Override
    public void buyHouse() {
        Log.e(TAG, "买房子");
    }
}
