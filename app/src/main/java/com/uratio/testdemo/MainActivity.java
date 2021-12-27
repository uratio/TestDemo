package com.uratio.testdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uratio.testdemo.utils.Utils;
import com.uratio.testdemo.animlist.AnimListActivity;
import com.uratio.testdemo.animlist.AnimRcvActivity;
import com.uratio.testdemo.animlist.ListViewActivity;
import com.uratio.testdemo.arithmetic.ArithmeticActivity;
import com.uratio.testdemo.designmodel.DesignModelActivity;
import com.uratio.testdemo.hexagon.HexagonActivity;
import com.uratio.testdemo.img.LongFigureActivity;
import com.uratio.testdemo.load.LoadAnimActivity;
import com.uratio.testdemo.parse.XmlParseActivity;
import com.uratio.testdemo.tools.ToolsActivity;
import com.uratio.testdemo.view.MyEditText;
import com.uratio.testdemo.view.ViewStubActivity;
import com.uratio.testdemo.workmanager.WorkManagerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private String msg = "提示的内容信息";
    private TextView textView;
    private MyEditText myEditText;
    private EditText editText;

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

        myEditText = findViewById(R.id.my_edit);
        editText = findViewById(R.id.edit_text);
        myEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG, "myEditText OnTouchListener: ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG, "myEditText OnTouchListener: ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "myEditText OnTouchListener: ACTION_UP");
                        break;
                }
                return false;
            }
        });
        myEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "myEditText OnClickListener:");
            }
        });

        myEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e(TAG, "myEditText setOnFocusChangeListener: hasFocus=" + hasFocus);
            }
        });
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG, "editText OnTouchListener: ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG, "editText OnTouchListener: ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "editText OnTouchListener: ACTION_UP");
                        break;
                }
                return false;
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "editText OnClickListener:");
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e(TAG, "editText setOnFocusChangeListener: hasFocus=" + hasFocus);
            }
        });
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
                intent.putExtra("url", "http://www.dajiuge.com");
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
            case R.id.btn_44://工具
                intent = new Intent(MainActivity.this, ToolsActivity.class);
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
            case R.id.btn_anim_list://动画列表list
                intent = new Intent(MainActivity.this, AnimListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_anim_rcv://动画列表rcv
                intent = new Intent(MainActivity.this, AnimRcvActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_anim_load://加载动画
                intent = new Intent(MainActivity.this, LoadAnimActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_set_focus://模拟点击edittext
                showSoftInputFromWindow(editText);
                break;
            case R.id.btn_view_stub:// ViewStub
                intent = new Intent(MainActivity.this, ViewStubActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_listView:// ViewStub
                intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_get_data:// 获取数据
                ArrayList<String> strings = new ArrayList<>();
                ArrayList<String> stringList = new ArrayList<>();
                strings.add("adfasfd");
                stringList.add(strings.get(strings.size() - 1));
                Log.e(TAG, "onClickView: stringList=" + stringList);
                queryTextCache.put("111", strings);
                Log.e(TAG, "onClickView: " + queryTextCache.get("123"));
                Log.e(TAG, "onClickView: " + queryTextCache.get("111"));
                break;
            case R.id.btn_xml_parse:// xml解析
                intent = new Intent(MainActivity.this, XmlParseActivity.class);
                startActivity(intent);
                break;
        }
    }

    private Map<String, ArrayList<String>> queryTextCache = new HashMap<>();

    /**
     * EditText获取焦点并显示软键盘
     */
    private void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setSelection(editText.getText().length());
        //显示软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
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
    public boolean isEmpty(String value) {
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
