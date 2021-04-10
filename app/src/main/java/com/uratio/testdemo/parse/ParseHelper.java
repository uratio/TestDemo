package com.uratio.testdemo.parse;

import com.uratio.testdemo.parse.msg.XmlConfig;
import com.uratio.testdemo.parse.msg.SubviewData;
import com.uratio.testdemo.parse.msg.ViewData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author lang
 * @data 2021/4/7
 */
public class ParseHelper {

    public static List<ViewData> parseXml(String xmlData) {
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

    private static ViewData parseView(Element element) {
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
}
