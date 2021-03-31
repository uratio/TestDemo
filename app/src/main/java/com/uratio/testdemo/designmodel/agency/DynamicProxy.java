package com.uratio.testdemo.designmodel.agency;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lang
 * @data 2021/3/2
 */
public class DynamicProxy implements InvocationHandler {
    private static final String TAG = DynamicProxy.class.getSimpleName();
    private Object object;

    public DynamicProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e(TAG,"invoke");
        Object invoke = method.invoke(object, args);
        return invoke;
    }
}
