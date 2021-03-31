package com.uratio.testdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ThirdActivity extends AppCompatActivity {

    private static final String TAG = "ThirdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Log.e(TAG, "onCreate: **************");

        String value = getIntent().getStringExtra("key");
        Log.i(TAG, "onCreate: value=" + value);
        Log.i(TAG, "onCreate: boolean=" + "123".equals(value));
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

    public void onClickView(View view) {
        switch (view.getId()){
            case R.id.btn_1:
                Intent intent = getIntent();
                intent.putExtra("data", 111);
                setResult(1001, intent);
                finish();
                break;
        }
    }
}
