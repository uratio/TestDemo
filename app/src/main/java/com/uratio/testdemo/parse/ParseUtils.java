package com.uratio.testdemo.parse;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

/**
 * @author lang
 * @data 2021/4/7
 */
public class ParseUtils {
    public static void parseXMLWithPull(String xmlData) {
        try {
            //首先获取一个XmlPullParserFactory的实例，主要调用其静态方法newInstance()得到。
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //然后利用XmlPullParserFactory的实例，调用newPullParser()方法，得到XmlPullParser对象。
            XmlPullParser xmlPullParser = factory.newPullParser();
            //然后调用XmlPullParser的setInput()方法将服务器返回的XML数据设置进去就可以开始解析了。
            // 注意：这里的StringReader类是Reader类的子类。
            //public class StringReaderextends ReaderAspecialized Reader that reads characters from a String in a sequential manner.
            xmlPullParser.setInput(new StringReader(xmlData));
            //具体解析的时候，首先通过XmlPullParser类的getEventType()方法得到当前的解析事件。
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            //然后在一个while循环中不断地进行解析，
            // 如果当前的解析事件不等于XmlPullParser.END_DOCUMENT,
            // 说明解析工作还没完成，调用XmlPullParser的next()方法可以获取下一个解析事件。
            while(eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    //开始解析某个节点
                    /*
                     * 在while循环中，通过XmlPullParser类的getName()方法得到当前结点的名字，
                     * 如果发现结点名等于id，name,或version，
                     * 就调用nextText()方法来获取结点内具体的内容
                     * ，每当解析完一个app结点后就将获取到的内容打印出来。
                     * */
                    case XmlPullParser.START_TAG:{
                        if ("id".equals(nodeName)){
                            id = xmlPullParser.nextText();
                        }else if("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        }else if ("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    //完成解析某个节点
                    case XmlPullParser.END_TAG:{
                        if ("app".equals(nodeName)){
                            Log.e("MainActivity","id is"+id);
                            Log.e("MainActivity","name is"+name);
                            Log.e("MainActivity","version is"+version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
