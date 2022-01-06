package com.uratio.testdemo.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.uratio.testdemo.MainActivity;
import com.uratio.testdemo.R;

/**
 * Android四种启动模式
 * 1.标准模式（standard）：
 * 每启动一次Activity，就会创建一个新的Activity实例并置于栈顶。谁启动了这个Activity，那么这个Activity就运行在启动它的那个Activity所在的栈中。
 * 应用场景：一般我们不主动设置启动模式，都是标准模式。
 *
 * 2.栈顶模式(singleTop)：
 * 如果栈顶存在该activity的实例，则复用，不存在新建放入栈顶。
 * 应用场景：（1）点击通知跳详情 （2）新闻详情页，点击推荐新闻条目
 *
 * 3.栈内模式(singleTask)：
 * 如果栈内存在该activity的实例，会将该实例上边的activity全部出栈，将该实例置于栈顶，如果不存在，则创建
 * 应用场景： （1）APP的home页面，如果跳转到其他页面后又要跳回来 （2）浏览器的主页
 *
 * 4.单例模式(singleInstance):
 * 新开一个任务栈，该栈内只存放当前实例
 * 应用场景：项目中语音通话功能，来电话显示页面采用的就是singleinstance模式
 *
 */
public class SingleTopActivity extends AppCompatActivity {
    private TextView tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);

        tvDesc = findViewById(R.id.tv_desc);

        String key = getIntent().getStringExtra("key");
        super.onStart();
        Log.i("startModel", "SingleTopActivity ***** onCreate: key=" +key);

        String data = getIntent().getStringExtra("data");
        tvDesc.setText("SingleTop界面\n" + data);
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.to_main:
                Intent intent = new Intent(SingleTopActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Log.i("startModel", "SingleTopActivity ***** intent: " + intent.getClass());
                startActivity(intent);
                break;
            case R.id.to_top:
                startActivity(new Intent(SingleTopActivity.this, SingleTopActivity.class));
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
        String key = intent.getStringExtra("data");
        Log.i("startModel", "SingleTopActivity ***** onNewIntent: data="+key);
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
