package com.uratio.testdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uratio.testdemo.utils.LogUtils;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";
    private WebView webView;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initView();

        initData();

        webView.loadUrl("http://130.1.11.220:9094/index.html#/profile");
    }

    private void initData() {
        String url = getIntent().getStringExtra("url");
        LogUtils.e("跳转的url=" + url);
        Log.e("test_demo_web", "跳转的url=" + url);
    }

    private void initView() {
        webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        //支持插件
        // webSettings.setPluginsEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        /*如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可*/
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);// 设置使用localStorage来进行缓存
        webView.addJavascriptInterface(new jsBridge(), "jsBridge");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e(TAG, "onProgressChanged：" + newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                XWebView.log("shouldOverrideUrlLoading Url：" + url);
                if (url == null) {
                    return false;
                }
                if (url.startsWith("http:") || url.startsWith("https:")) {
//            view.loadUrl(url);
                    return false;
                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
//                        XWebView.log("url  Exception shouldOverrideUrlLoading"  );
                    }
                }
                return true;
            }
        });
    }

    class jsBridge {

        //获取档案号
        @JavascriptInterface
        public void getFileNumber(String method) {
            callJs("javascript:getUserId('0000020,ae81d6a3882a475a9ed540f9e926356c,d4ca9f016690472c')");
        }

    }


    /**
     * "javascript:funJs('Android端传入的信息，div标签中显示,含参数')"
     * "javascript:funJs()"
     *
     * @param funJs
     */
    public void callJs(String funJs) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

//        log("callJs funJs：" + funJs);
                if (Build.VERSION.SDK_INT > 19) {
                    webView.evaluateJavascript(funJs, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
//                    log("onReceiveValue value：" + value);
                        }
                    });
                } else {
                    webView.loadUrl(funJs);
                }
            }
        });
    }

}
