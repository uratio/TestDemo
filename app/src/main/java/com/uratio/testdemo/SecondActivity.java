package com.uratio.testdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    private static Inner1 inner1 = null;
    private static Inner2 inner2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        inner1 = new Inner1();
        inner2 = new Inner2();
        new Thread(runnable).start();
        Log.e(TAG, "onCreate: **************");

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    public void onClickView(View view) {
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        switch (view.getId()){
            case R.id.btn_1:
                startActivity(intent);
                break;
            case R.id.btn_2:
                startActivityForResult(intent, 1000);
                break;
        }
    }

    /**
     * 内存泄漏原因
     */
    private class Inner1 {
        //非静态内部类，创建静态示例，会长期持有该 Activity 对象，造成Activity需要销毁的时候没法正常销毁，导致内存泄漏
        //使用debug可以看到
    }

    private static class Inner2 {
        //静态内部类，创建静态示例，不会持有该 Activity 对象，不会内存泄漏
    }

    private Runnable runnable = new Runnable() {
        //runnable 是非静态匿名内部类，会自动持有外部类 Activity 的引用
        //但是runnable被异步线程Thread使用，这样就会导致 Activity 在销毁的时候没法正常销毁，从而引起内存泄漏

        /**
         * 解决办法，可以把 runnable 设置为静态内部类
         */
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: **************");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: **************");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onRestart: **************");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: **************");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: **************");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: **************");
    }
}
