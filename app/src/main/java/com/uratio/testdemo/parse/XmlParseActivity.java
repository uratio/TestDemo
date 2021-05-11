package com.uratio.testdemo.parse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.uratio.pikers.util.ConvertUtils;
import com.uratio.testdemo.R;
import com.uratio.testdemo.parse.msg.MessageData;
import com.uratio.testdemo.parse.msg.ViewData;

import org.xml.sax.InputSource;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class XmlParseActivity extends AppCompatActivity {
    private String xmlData = "<apps>\n" +
            "    <app>\n" +
            "        <id>1</id>\n" +
            "        <name>Google Maps</name>\n" +
            "        <version>1.0</version>\n" +
            "    </app>\n" +
            "    <app>\n" +
            "        <id>2</id>\n" +
            "        <name>Chrome</name>\n" +
            "        <version>2.1</version>\n" +
            "    </app>\n" +
            "    <app>\n" +
            "        <id>3</id>\n" +
            "        <name>Google Play</name>\n" +
            "        <version>2.3</version>\n" +
            "    </app>\n" +
            "</apps>\n";

    private String xmlData2 = "\n" +
            "<beauties>  \n" +
            "    <beauty>  \n" +
            "        <name>范冰冰</name>  \n" +
            "        <age>28</age>  \n" +
            "    </beauty>  \n" +
            "    <beauty>  \n" +
            "        <name>杨幂</name>  \n" +
            "        <age>23</age>  \n" +
            "    </beauty>  \n" +
            "  \n" +
            "</beauties>";

    private String xmlData3 = "<p>&lt;message&gt;</p>\n" +
            "    <p>&lt;view&gt;</p>\n" +
            "        <p>&lt;type&gt;label<p>&lt;/type&gt;</p>\n" +
            "        <p>&lt;font&gt;15<p>&lt;/font&gt;</p>\n" +
            "    <p>&lt;/view&gt;</p>\n" +
            "<p>&lt;/message&gt;</p>";

    private TextView tvShow;
    private EditText etAlphaTurn;
    private TextView tvAlphaTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parse);

        tvShow = findViewById(R.id.tv_show);
        etAlphaTurn = findViewById(R.id.et_alpha_turn);
        tvAlphaTurn = findViewById(R.id.tv_alpha_turn);
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_pull:
                SaxUtils.parseXMLWithPull(xmlData);
                break;
            case R.id.btn_sax:
                SaxParse.parse(xmlData2);
                break;
            case R.id.btn_recursion:
                try {
                    for (int i = 0; i < 30; i++) {
                        List<ViewData> viewData = SaxUtils.parseRecursion(new InputSource(getAssets().open("messages2.xml")));
                        Log.e("data", "打印（" + (i+1) + ")）: " + new Gson().toJson(viewData));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_recursion2:
                List<ViewData> viewData2 = SaxUtils.parseRecursion(translation(xmlData3));
                Log.e("data", "打印: " + new Gson().toJson(viewData2));
                break;
            case R.id.btn_element:
                try {
                    SaxUtils.parseElement(new InputSource(getAssets().open("messages2.xml")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_other:
                try {
                    MessageData messageData = SaxUtils.converyToJavaBean(ConvertUtils.toString(getAssets().open(
                            "messages.xml")), MessageData.class);
                    Log.e("data", "onClickView: messageData=" + new Gson().toJson(messageData));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_bg:
                tvShow.setBackgroundColor(Color.parseColor("#181D36"));
                break;
            case R.id.btn_bg2:
//                tvShow.setBackgroundColor(Color.parseColor("181D36"));
                break;
            case R.id.btn_package_name:
                loadApps();
                break;
            case R.id.btn_size:
                tvShow.setTextSize(20);
                break;
            case R.id.btn_color:
                tvShow.setTextColor(Color.parseColor("181D36"));
                break;
            case R.id.btn_bold:
                ViewData viewData = new ViewData();
                viewData.setBold("1");
                tvShow.setTypeface(Typeface.defaultFromStyle(viewData.getBold()));
                break;
            case R.id.btn_alpha_turn:
                if (TextUtils.isEmpty(etAlphaTurn.getText().toString())) return;
                tvAlphaTurn.setText(turnAlpha(etAlphaTurn.getText().toString()));
                break;
        }

    }



    private void loadApps() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> apps = getPackageManager().queryIntentActivities(intent, 0);
        //排序
        Collections.sort(apps, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo a, ResolveInfo b) {
                return String.CASE_INSENSITIVE_ORDER.compare(
                        a.loadLabel(getPackageManager()).toString(),
                        b.loadLabel(getPackageManager()).toString()
                );
            }
        });
        //for循环遍历ResolveInfo对象获取包名和类名
        for (int i = 0; i < apps.size(); i++) {
            ResolveInfo info = apps.get(i);
            String packageName = info.activityInfo.packageName;
            CharSequence cls = info.activityInfo.name;
            CharSequence name = info.activityInfo.loadLabel(getPackageManager());
            //log打印
            Log.e("ddddddd",name+"----"+packageName+"----"+cls);
        }
    }

    private String translation(String content) {
        return content
                .replace("<p>", "")
                .replace("</p>", "")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .replace("&quot;", "\"")
                .replace("&copy;", "©")
                ;
    }

    public String turnAlpha(String alpha) {
        String result = Integer.toHexString((int) Math.ceil(255 * (100 - Double.parseDouble(alpha)) / 100)).toUpperCase();
        if (result.length() == 1) {
            result = "0" + result;
        }
        return result;
    }

    /**
     * 设置圆角的背景
     *
     * @param context 上下文
     * @param v       View
     */
    public void shapeSolid(Context context, View v, int pos) {
        GradientDrawable gd = (GradientDrawable) v.getBackground();
        int strokeWidth = 1; // 1dp 边框宽度
        int roundRadius = 8; // 8dp 圆角半径
        int strokeColor = 0xffff4984;//边框颜色
        int fillColor = 0xffffffff;//内部填充颜色
        if (pos == 1) {
            strokeColor = 0xff02FF13;//边框颜色
            fillColor = 0xff02FF13;//内部填充颜色
        }
        gd.setColor(fillColor);
        gd.setCornerRadius(dp2px(context, roundRadius));
        gd.setStroke(dp2px(context, strokeWidth), strokeColor);
    }

    /**
     * 根据手机的分辨率dp 转成px(像素)
     */
    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
