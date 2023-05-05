package com.uratio.testdemo.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.uratio.testdemo.R;

public class SingleTaskActivity extends AppCompatActivity {
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
    }

    public void onClickView(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.to_top:
                count++;
                Intent intent1 = new Intent(SingleTaskActivity.this, SingleTopActivity.class);
                intent1.putExtra("data", "SingleTask页面过来：" + count);
                startActivity(intent1);
                break;
            case R.id.to_task:
                startActivity(new Intent(SingleTaskActivity.this, SingleTaskActivity.class));
                break;
            case R.id.to_instance:
                intent = new Intent(SingleTaskActivity.this, SingleInstanceActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("startModel", "SingleTaskActivity ***** onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("startModel", "SingleTaskActivity ***** onRestart: ");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("startModel", "SingleTaskActivity ***** onNewIntent: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("startModel", "SingleTaskActivity ***** onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("startModel", "SingleTaskActivity ***** onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("startModel", "SingleTaskActivity ***** onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("startModel", "SingleTaskActivity ***** onDestroy: ");
    }
}
