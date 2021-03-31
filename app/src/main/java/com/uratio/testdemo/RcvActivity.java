package com.uratio.testdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class RcvActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcv);

        recyclerView = findViewById(R.id.recyclerView);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("父级 i=" + i);
        }
        RcvAdapter adapter = new RcvAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
