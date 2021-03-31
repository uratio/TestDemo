package com.uratio.testdemo.load;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.uratio.testdemo.R;
import com.wang.avi.AVLoadingIndicatorView;

public class IndicatorActivity extends AppCompatActivity {
    private AVLoadingIndicatorView avi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        String indicator = getIntent().getStringExtra("indicator");
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator(indicator);
    }

    public void hideClick(View view) {
        avi.hide();
        // or avi.smoothToHide();
    }

    public void showClick(View view) {
        avi.show();
        // or avi.smoothToShow();
    }

}
