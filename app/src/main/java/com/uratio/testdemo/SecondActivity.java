package com.uratio.testdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.e(TAG, "onCreate: **************");
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
