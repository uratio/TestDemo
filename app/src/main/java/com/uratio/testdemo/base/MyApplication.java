package com.uratio.testdemo.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.uratio.testdemo.BuildConfig;
import com.uratio.testdemo.utils.CommonConfig;

/**
 * @author lang
 * @data 2020/11/13
 */
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {


    private static final String TAG = "MyApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        System.out.println("****************  MyApplication attachBaseContext  ******************");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("****************  MyApplication onCreate  ******************");
        registerActivityLifecycleCallbacks(this);

        CommonConfig.init(BuildConfig.DEBUG, "host_address");

        //Android 11 引入了此方法，用于报告近期任何进程终止的原因
        //ActivityManager.getHistoricalProcessExitReasons();
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "****** onActivityCreated *****" + activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Log.e(TAG, "****** onActivityStarted *****" + activity.getLocalClassName());
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Log.e(TAG, "****** onActivityResumed *****" + activity.getLocalClassName());
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Log.e(TAG, "****** onActivityPaused *****" + activity.getLocalClassName());
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Log.e(TAG, "****** onActivityStopped *****" + activity.getLocalClassName());
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        Log.e(TAG, "****** onActivitySaveInstanceState *****" + activity.getLocalClassName());
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Log.e(TAG, "****** onActivityDestroyed *****" + activity.getLocalClassName());
    }
}
