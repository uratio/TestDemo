package com.uratio.testdemo.hexagon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.uratio.testdemo.R;

/**
 * 六边形
 */
public class HexagonActivity extends AppCompatActivity {
    private static final String TAG = "HexagonActivity";
    private HexagonView hexagon;
    private HexagonLayout hl;
    private String[] arrBrnText = {"按钮1", "按钮2", "按钮3", "按钮4", "按钮5"};

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sexangle);

        setFlag();

        layout = findViewById(R.id.layout);
        hexagon = findViewById(R.id.hexagon);
        hl = findViewById(R.id.hl);
        hexagon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: 点击了六边形内部");
            }
        });

        Log.e(TAG, "HexagonLayout 走了 init");
        int width = getResources().getDisplayMetrics().widthPixels;
        hl.init(width * 70 / 750, width * 70 / 750, width * 70 / 750, width * 125 / 750, 3, arrBrnText);
        hl.setOnClickDexagonListener(new HexagonLayout.OnClickDexagonListener() {
            @Override
            public void OnClick(int index) {
                switch (index) {
                    case 0:
                        Log.e(TAG, "onClick: 点击了 按钮1");
                        break;
                    case 1:
                        Log.e(TAG, "onClick: 点击了 按钮2");
                        break;
                    case 2:
                        Log.e(TAG, "onClick: 点击了 按钮3");
                        break;
                    case 3:
                        Log.e(TAG, "onClick: 点击了 按钮4");
                        break;
                    case 4:
                        Log.e(TAG, "onClick: 点击了 按钮5");
                        break;
                }
            }
        });

        Log.e(TAG, "******** onCreate: ");
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.h1:
                Log.e(TAG, "onClick: 点击了六边形内部 *** 1 ***");
                break;
            case R.id.h2:
                Log.e(TAG, "onClick: 点击了六边形内部 *** 2 ***");
                break;
            case R.id.h3:
                Log.e(TAG, "onClick: 点击了六边形内部 *** 3 ***");
                break;
            case R.id.h4:
                Log.e(TAG, "onClick: 点击了六边形内部 *** 4 ***");
                break;
            case R.id.h5:
                Log.e(TAG, "onClick: 点击了六边形内部 *** 5 ***");
                break;
            case R.id.add_data:
                break;
            case R.id.button:
                try {
                    Intent panelIntent = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
                    startActivityForResult(panelIntent, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "onClickView: button  e="+ e.getMessage());
                }
                break;
        }
    }

    private void setFlag() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    private void setViewFlag() {
        WindowManager.LayoutParams mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_SECURE;
        WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        View view = new View(this);
//        view.setMinimumWidth(widthPixels);
//        view.setMinimumHeight(heightPixels);
        view.setMinimumWidth(200);
        view.setMinimumHeight(300);
        view.setBackgroundColor(Color.RED);
        mWindowManager.addView(view, mWindowLayoutParams);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "******** onStart: ");
    }

    @Override
    protected void onRestart() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        super.onRestart();
        Log.e(TAG, "******** onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "******** onResume: ");
    }

    @Override
    protected void onPause() {
//        setViewFlag();
        super.onPause();
        Log.e(TAG, "******** onPause: ");
    }

    @Override
    protected void onStop() {
        setFlag();
        super.onStop();
        Log.e(TAG, "******** onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "******** onDestroy: ");
    }
}
