package com.uratio.testdemo.parse;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class MyDefaultHandler extends DefaultHandler {
    private static final String TAG = "MyDefaultHandler";
    private List<Book> bookList;
    private Book book;
    String localTag;//标签名

    //遇到开始文档执行的方法
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    //遇到结束文档执行的方法
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    //遇到开始标签执行的方法
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        localTag = localName;//保存标签名，方便接下来处理内容字符串。

        if (localName.equals("BookSet")) {
            bookList = new ArrayList<>();
        } else if (localName.equals("Book")) {
            book = new Book();
            String id = attributes.getValue(0);
            book.setId(id);
        }
    }

    //遇到结束标签执行的方法
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equals("Book")) {
            bookList.add(book);
        }
    }

    //遇到标签内容字符串执行的方法
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if (localTag.equals("name")) {
            book.setName(new String(ch, start, length));
        } else if (localTag.equals("author")) {
            book.setAuthor(new String(ch, start, length));
        } else if (localTag.equals("page")) {
            book.setPage(new String(ch, start, length));
        } else if (localTag.equals("price")) {
            book.setPrice(new String(ch, start, length));
        }

    }

    //返回解析好的集合数据
    public List<Book> getBookList() {
        return bookList;
    }
}