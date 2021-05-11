package com.uratio.testdemo.parse;

import android.util.Log;

import com.google.gson.Gson;
import com.uratio.testdemo.parse.msg.XmlConfig;
import com.uratio.testdemo.parse.msg.SubviewData;
import com.uratio.testdemo.parse.msg.ViewData;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author lang
 * @data 2021/4/7
 */
public class SaxUtils {
    private static final String TAG = SaxUtils.class.getSimpleName();

    private static final SAXParserFactory factory = SAXParserFactory.newInstance();

    public static List<ViewData> parse(String xmlData) throws Exception {
        SAXParser parser = factory.newSAXParser();
        XMLReader reader = parser.getXMLReader();

        MsgSaxHandler mySaxHandler = new MsgSaxHandler();
        reader.setContentHandler(mySaxHandler);
        reader.parse(new InputSource(new ByteArrayInputStream(xmlData.getBytes("UTF-8"))));

        ArrayList<ViewData> viewData = mySaxHandler.getViewData();
//        Log.e(TAG, "viewData=" + viewData);
        Log.e(TAG, "viewDataJson=" + new Gson().toJson(viewData));
        return viewData;
    }

    public static List<ViewData> parseRecursion(String xmlData) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource inputSource = new InputSource(new ByteArrayInputStream(xmlData.getBytes("UTF-8")));
            Document document = db.parse(inputSource);
            Element root = document.getDocumentElement();
            return parseList(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ViewData> parseRecursion(InputSource is) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.parse(is);
        Element root = document.getDocumentElement();
        return parseList(root);
    }

    private static List<ViewData> parseList(Element element) {
        List<ViewData> viewList = new ArrayList<>();
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            // 获取每一个child
            Node node = children.item(i);
            // 获取节点类型
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // 如果是元素类型，则递归输出
                Element child = (Element) node;
                if (XmlConfig.XmlKey.VIEW.equals(child.getNodeName())) {
                    viewList.add(parseView(child));
                }
            }
        }
        return viewList;
    }

    static ViewData parseView(Element element) {
        ViewData viewData = new ViewData();
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            // 获取每一个child
            Node node = children.item(i);
            // 获取节点类型
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // 如果是元素类型，则递归输出
                Element child = (Element) node;
                switch (child.getNodeName()) {
                    case XmlConfig.XmlKey.TYPE:
                        viewData.setType(parseValue(child));
                        break;
                    case XmlConfig.XmlKey.TEXT:
                        viewData.setText(parseValue(child));
                        break;
                    case XmlConfig.XmlKey.BOLD:
                        viewData.setBold(parseValue(child));
                        break;
                    case XmlConfig.XmlKey.FRONT:
                        viewData.setFront(parseValue(child));
                        break;
                    case XmlConfig.XmlKey.TEXT_COLOR:
                        viewData.setTextColor(parseValue(child));
                        break;
                    case XmlConfig.XmlKey.BACKGROUND_COLOR:
                        viewData.setBackgroundColor(parseValue(child));
                        break;
                    case XmlConfig.XmlKey.URL:
                        viewData.setUrl(parseValue(child));
                        break;
                    case XmlConfig.XmlKey.IS_QUESTION:
                        viewData.setIsQuestion(parseValue(child));
                        break;
                    case XmlConfig.XmlKey.SUBVIEWS:
                        SubviewData subviewData = new SubviewData();
                        subviewData.setViews(parseList(child));
                        viewData.setSubviews(subviewData);
                        break;
                }
            }
        }
        return viewData;
    }

    private static String parseValue(Element element) {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            // 获取每一个child
            Node node = children.item(i);
            // 获取节点类型
            if (node.getNodeType() == Node.TEXT_NODE) {
                // 如果是文本类型，则输出节点值，及文本内容
                return node.getNodeValue();
            }
        }
        return "";
    }

    public static List<ViewData> parseElement(InputSource is) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.parse(is);
        Element root = document.getDocumentElement();
        parseElement3(root);
        return null;
    }

    static void parseElement3(Element element) {
        String tagName = element.getNodeName();

        System.out.print("<开始节点" + tagName);
        // element元素的所有属性构成的NamedNodeMap对象，需要对其进行判断
        NamedNodeMap map = element.getAttributes();
        // 如果存在属性，则打印属性
        if (null != map) {
            for (int i = 0; i < map.getLength(); i++) {
                // 获得该元素的每一个属性
                Attr attr = (Attr) map.item(i);
                // 属性名和属性值
                String attrName = attr.getName();
                String attrValue = attr.getValue();
                // 注意属性值需要加上引号，所以需要\转义
                System.out.print(" " + attrName + "=\"" + attrValue + "\"");
            }
        }

        // 关闭标签名
        System.out.print(">");

        // 至此已经打印出了元素名和其属性
        // 下面开始考虑它的子元素
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            // 获取每一个child
            Node node = children.item(i);
            // 获取节点类型
            short nodeType = node.getNodeType();

            if (nodeType == Node.ELEMENT_NODE) {
                // 如果是元素类型，则递归输出
                System.out.print("ELEMENT_NODE=" + i);
                parseElement3((Element) node);
            } else if (nodeType == Node.TEXT_NODE) {
                // 如果是文本类型，则输出节点值，及文本内容
                System.out.print("TEXT_NODE=" + i + node.getNodeValue());
            } else if (nodeType == Node.COMMENT_NODE) {
                // 如果是注释，则输出注释
                System.out.print("<!--");
                Comment comment = (Comment) node;
                // 注释内容
                String data = comment.getData();

                System.out.print(data);

                System.out.print("-->");
            }
        }

        // 所有内容处理完之后，输出，关闭根节点
        System.out.print("</" + tagName + ">");
    }

    /**
     * xml转换成JavaBean
     * @param xml
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T converyToJavaBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

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
