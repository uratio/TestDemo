package com.uratio.testdemo.parse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.uratio.pikers.util.ConvertUtils;
import com.uratio.testdemo.R;

import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parse);

    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_parse:
                ParseUtils.parseXMLWithPull(xmlData);
                break;
            case R.id.btn_parse2:
                SaxParse.parse(xmlData2);
                break;
            case R.id.btn_parse3:
                try {
                    SaxUtils.parse(ConvertUtils.toString(getAssets().open("messages.xml")));
                    SaxUtils.parse2(new InputSource(getAssets().open("messages2.xml")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
