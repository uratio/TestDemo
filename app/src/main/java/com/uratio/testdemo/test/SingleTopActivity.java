package com.uratio.testdemo.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.uratio.testdemo.MainActivity;
import com.uratio.testdemo.R;

public class SingleTopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);

        String key = getIntent().getStringExtra("key");
        super.onStart();
        Log.i("startModel", "SingleTopActivity ***** onCreate: key=" +key);
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.to_main:
                Intent intent = new Intent(SingleTopActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Log.i("startModel", "SingleTopActivity ***** intent: " + intent.getClass());
                startActivity(intent);
                break;
            case R.id.to_task:
                startActivity(new Intent(SingleTopActivity.this, SingleTaskActivity.class));
                break;
            case R.id.to_instance:
                startActivity(new Intent(SingleTopActivity.this, SingleInstanceActivity.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("startModel", "SingleTopActivity ***** onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("startModel", "SingleTopActivity ***** onRestart: ");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String key = intent.getStringExtra("key");
        Log.i("startModel", "SingleTopActivity ***** onNewIntent: key="+key);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("startModel", "SingleTopActivity ***** onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("startModel", "SingleTopActivity ***** onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("startModel", "SingleTopActivity ***** onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("startModel", "SingleTopActivity ***** onDestroy: ");
    }
}
