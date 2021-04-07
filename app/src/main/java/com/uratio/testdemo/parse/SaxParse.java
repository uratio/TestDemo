package com.uratio.testdemo.parse;

import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author lang
 * @data 2021/4/7
 */
public class SaxParse {
    private static final String TAG = SaxParse.class.getSimpleName();

    private static final SAXParserFactory factory = SAXParserFactory.newInstance();

    public static void parse(String xmlData) {


        try {
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();

            MyDefaultHandler handler = new MyDefaultHandler();
//            reader.setContentHandler(handler);
            MySaxHandler mySaxHandler = new MySaxHandler();
            reader.setContentHandler(mySaxHandler);
            reader.parse(new InputSource(new ByteArrayInputStream(xmlData.getBytes("UTF-8"))));


//            List<Book> bookList = handler.getBookList();
//            Log.e(TAG, "parse: " + bookList.toString());
            ArrayList<Beauty> arrayList = mySaxHandler.getmList();
            Log.e(TAG, "parse: " + arrayList.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
