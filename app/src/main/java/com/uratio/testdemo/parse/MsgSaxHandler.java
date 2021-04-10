package com.uratio.testdemo.parse;

import android.util.Log;

import com.uratio.testdemo.parse.msg.XmlConfig;
import com.uratio.testdemo.parse.msg.SubviewData;
import com.uratio.testdemo.parse.msg.ViewData;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class MsgSaxHandler extends DefaultHandler {

    private ArrayList<ViewData> views = new ArrayList<>();
    private ViewData viewData;
    private SubviewData subviews = new SubviewData();
    private SubviewData subviewData;
    private String labelType;
    //声明一个字符串变量
    private String content;

    public ArrayList<ViewData> getViewData() {
        return views;
    }

    /**
     * 当SAX解析器解析到XML文档开始时，会调用的方法
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    /**
     * 当SAX解析器解析到XML文档结束时，会调用的方法
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    /**
     * 当SAX解析器解析到某个元素开始时，会调用的方法
     * 其中localName记录的是元素属性名
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        Log.e("dat", "startElement: uri=" + uri);
        Log.e("dat", "startElement: localName=" + localName);
        Log.e("dat", "startElement: qName=" + qName);
        Log.e("dat", "startElement: attributes=" + attributes);
        if (XmlConfig.XmlKey.VIEW.equals(localName)) {
            viewData = new ViewData();
            labelType = localName;
        } else if (XmlConfig.XmlKey.SUBVIEWS.equals(localName)) {
            subviewData = new SubviewData();
            labelType = localName;
        }
    }

    /**
     * 当SAX解析器解析到某个元素结束时，会调用的方法
     * 其中localName记录的是元素属性名
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);

        switch (localName) {
            case XmlConfig.XmlKey.VIEW:
                if (labelType.equals(XmlConfig.XmlKey.VIEW)) {
                    views.add(viewData);
                } else {
                    subviews.setViews(views);
                }
                break;
            case XmlConfig.XmlKey.TYPE:
                viewData.setType(content);
                break;
            case XmlConfig.XmlKey.TEXT:
                viewData.setText(content);
                break;
            case XmlConfig.XmlKey.BOLD:
                viewData.setBold(content);
                break;
            case XmlConfig.XmlKey.FRONT:
                viewData.setFront(content);
                break;
            case XmlConfig.XmlKey.TEXT_COLOR:
                viewData.setTextColor(content);
                break;
            case XmlConfig.XmlKey.BACKGROUND_COLOR:
                viewData.setBackgroundColor(content);
                break;
            case XmlConfig.XmlKey.URL:
                viewData.setUrl(content);
                break;
            case XmlConfig.XmlKey.IS_QUESTION:
                viewData.setIsQuestion(content);
                break;
            case XmlConfig.XmlKey.SUBVIEWS:
                viewData.setSubviews(subviews);

                break;
        }
    }

    /**
     * 当SAX解析器解析到某个属性值时，会调用的方法
     * 其中参数ch记录了这个属性值的内容
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        content = new String(ch, start, length);
    }
}