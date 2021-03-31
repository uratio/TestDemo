package com.uratio.testdemo.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.view.View;

import com.uratio.testdemo.R;

public class WorkManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager);


        WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class).build();

        findViewById(R.id.btn_turn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WorkManager
                        .getInstance(WorkManagerActivity.this)
                        .enqueue(uploadWorkRequest);

            }
        });
    }
}
