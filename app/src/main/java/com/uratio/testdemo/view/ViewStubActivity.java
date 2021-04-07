package com.uratio.testdemo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import com.uratio.testdemo.R;

public class ViewStubActivity extends AppCompatActivity {
    private ViewStub viewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);


        viewStub = findViewById(R.id.viewStub);
        viewStub.inflate();
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                viewStub.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_hide:
                viewStub.setVisibility(View.GONE);
                break;
        }
    }
}
