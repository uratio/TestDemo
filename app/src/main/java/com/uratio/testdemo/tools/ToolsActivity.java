package com.uratio.testdemo.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uratio.testdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ToolsActivity extends AppCompatActivity implements ToolsRcvAdapter.OnClickItemListener {
    private static final String TAG = ToolsActivity.class.getSimpleName();
    private Activity activity;
    private RecyclerView recyclerView;
    private ToolsRcvAdapter adapter;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        activity = this;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new ToolsRcvAdapter(this, dataList);
        recyclerView.setAdapter(adapter);


        adapter.setListener(this);
        dataList.add("打印照片");
        dataList.add("打印HTML");

    }

    @Override
    public void onClickItem(int position, String type) {
        switch (type) {
            case "打印照片":
                doPhotoPrint();
                break;
            case "打印HTML":
                doWebViewPrint();
                break;
        }
    }

    /**
     * 打印照片
     */
    private void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(activity);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        //获取到bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_long_figure);
        photoPrinter.printBitmap("droids.jpg - test print", bitmap);
    }

    /**
     * 打印HTML
     */
    private WebView mWebView;

    private void doWebViewPrint() {
        // Create a WebView object specifically for printing
        WebView webView = new WebView(activity);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "page finished loading " + url);
                /**
                 * 请确保在WebViewClient)中的onPageFinished()方法内调用创建打印任务的方法。
                 * 如果没有等到页面加载完毕就进行打印，打印的输出可能会不完整或空白，甚至可能会失败。
                 */
                createWebPrintJob(view);
                mWebView = null;
            }
        });

        // Generate an HTML document on the fly:
        String htmlDocument = "<html><body><h1>Test Content</h1><p>Testing, " +
                "testing, testing...</p></body></html>";
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;
    }

    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            printManager = (PrintManager)activity.getSystemService(Context.PRINT_SERVICE);
        }

        String jobName = getString(R.string.app_name) + " Document";

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            printAdapter = webView.createPrintDocumentAdapter(jobName);
        }

        // Create a print job with name and adapter instance
        PrintJob printJob = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            printJob = printManager.print(jobName, printAdapter,
                    new PrintAttributes.Builder().build());
        }

        // 保存作业对象以便以后进行状态检查
        List<PrintJob> printJobs = new ArrayList<>();
        printJobs.add(printJob);
    }
}