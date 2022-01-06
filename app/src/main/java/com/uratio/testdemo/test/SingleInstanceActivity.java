package com.uratio.testdemo.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.uratio.testdemo.R;

public class SingleInstanceActivity extends AppCompatActivity {
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
    }

    public void onClickView(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.to_top:
                count++;
                intent = new Intent(SingleInstanceActivity.this, SingleTopActivity.class);
                intent.putExtra("data", "SingleInstance页面过来：" + count);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.to_task:
                startActivity(new Intent(SingleInstanceActivity.this, SingleTaskActivity.class));
                break;
            case R.id.to_instance:
                startActivity(new Intent(SingleInstanceActivity.this, SingleInstanceActivity.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("startModel", "SingleInstanceActivity ***** onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("startModel", "SingleInstanceActivity ***** onRestart: ");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("startModel", "SingleInstanceActivity ***** onNewIntent: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("startModel", "SingleInstanceActivity ***** onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("startModel", "SingleInstanceActivity ***** onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("startModel", "SingleInstanceActivity ***** onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("startModel", "SingleInstanceActivity ***** onDestroy: ");
    }
}
