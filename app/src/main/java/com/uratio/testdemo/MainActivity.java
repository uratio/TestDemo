package com.uratio.testdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.uratio.testdemo.Utils.Utils;
import com.uratio.testdemo.animlist.AnimRcvActivity;
import com.uratio.testdemo.designmodel.DesignModelActivity;
import com.uratio.testdemo.hexagon.HexagonActivity;
import com.uratio.testdemo.img.LongFigureActivity;
import com.uratio.testdemo.load.LoadAnimActivity;
import com.uratio.testdemo.workmanager.WorkManagerActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String msg = "提示的内容信息";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        String str1 = "0";
        String str2 = "0,";
        String str3 = "收支.show";

        String[] split1 = str1.split(",");
        String[] split2 = str2.split(",");
        System.out.println("打印1：" + split1[0]);
        System.out.println("打印2：" + split2[0]);
        System.out.println("contains：" + str2.contains(","));
        System.out.println("show：" + str3.replace(".show", ""));


        List<String> list = new ArrayList<>();
        LinkedList<String> linkedList = new LinkedList<>();

        System.out.println("list == null：" + (list == null));
        System.out.println("list.size()=" + list.size());
        list.add("strfdf");
        System.out.println("list == null：" + (list == null));
        System.out.println("list.size()=" + list.size());
    }

    @Override
    public void reportFullyDrawn() {
        super.reportFullyDrawn();
        System.out.println("****************  reportFullyDrawn  ******************");
    }

    public void onClickView(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_11://跳转2
                intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_12://WebView
                intent = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_13://toast
                Toast toast = Toast.makeText(MainActivity.this, msg += msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
                break;
            case R.id.btn_14://文字
                number++;
                if (number % 2 == 0) {
                    setText("5.45%");
                } else {
                    setText("5.41%-5.5%");
                }
                break;
            case R.id.btn_21://跳转3
                intent = new Intent(MainActivity.this, ThirdActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "123");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btn_22://标题界面
                intent = new Intent(MainActivity.this, TitleChangeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_31://跳转Rcv
                intent = new Intent(MainActivity.this, RcvActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_32://六边形
                intent = new Intent(MainActivity.this, HexagonActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_33://选择器
                intent = new Intent(MainActivity.this, PickersActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_41://统计应用
                Utils.getAllAppNames(MainActivity.this);
//                Log("e", "1234567890123456789");
                break;
            case R.id.btn_42://WorkManager
                intent = new Intent(MainActivity.this, WorkManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_43://计算
                intent = new Intent(MainActivity.this, ArithmeticActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_51://设计模式
                intent = new Intent(MainActivity.this, DesignModelActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_52://长图
                intent = new Intent(MainActivity.this, LongFigureActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_anim_list://动画列表
                intent = new Intent(MainActivity.this, AnimRcvActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_anim_load://加载动画
                intent = new Intent(MainActivity.this, LoadAnimActivity.class);
                startActivity(intent);
                break;
        }
    }

    private int number = 0;

    public void setText(String text) {
        if (isEmpty(text)) return;
        SpannableString ss = new SpannableString(text);
        int end = text.length();
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_text)), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (text.contains("-")) {
            int mid = text.indexOf("%");
            ss.setSpan(new AbsoluteSizeSpan(21, true), 0, mid, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(Typeface.BOLD), 0, mid, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(9, true), mid, mid + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(21, true), mid + 2, end - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(Typeface.BOLD), mid + 2, end - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(9, true), end - 1, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            ss.setSpan(new AbsoluteSizeSpan(30, true), 0, end - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(Typeface.BOLD), 0, end - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(12, true), end - 1, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(ss);
    }

    /**
     * <li>判断字符串是否为空值</li> <li>NULL、空格均认为空值</li>
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return null == value || "".equals(value.trim());
    }

    private void Log(String type, String msg) {
        String tag = "data";
        int index = 0;
        int maxLength = 5;
        String sub = "";
        while (index < msg.length()) {
            // java的字符不允许指定超过总的长度end
            if (msg.length() <= index + maxLength) {
                sub = msg.substring(index);
            } else {
                sub = msg.substring(index, index + maxLength);
            }
            index += maxLength;
            switch (type) {
                case "i":
                    Log.i(tag, sub);
                    break;
                case "d":
                    Log.d(tag, sub);
                    break;
                case "e":
                    Log.e(tag, sub);
                    break;
            }
        }
    }
}
