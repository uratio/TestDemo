package com.uratio.testdemo.parse.msg;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author lang
 * @data 2021/4/7
 */
public class ViewData implements Serializable {
    @XmlElement(name = Constants.XmlKey.TYPE)
    private String type;
    @XmlElement(name = Constants.XmlKey.TEXT)
    private String text;
    @XmlElement(name = Constants.XmlKey.BOLD)
    private String bold;
    @XmlElement(name = Constants.XmlKey.FRONT)
    private String front;
    @XmlElement(name = Constants.XmlKey.TEXT_COLOR)
    private String textColor;
    @XmlElement(name = Constants.XmlKey.BACKGROUND_COLOR)
    private String backgroundColor;
    private String bgAlpha;
    @XmlElement(name = Constants.XmlKey.URL)
    private String url;

    //
    @XmlElement(name = Constants.XmlKey.IS_QUESTION)
    private String isQuestion;
    @XmlElement(name = Constants.XmlKey.SUBVIEWS)
    private List<SubviewData> subviews;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBold() {
        return bold;
    }

    public void setBold(String bold) {
        this.bold = bold;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        String[] split = backgroundColor.split(",");
        if (split.length == 1) {
            this.backgroundColor = backgroundColor;
        } else if (split.length == 2) {
            this.backgroundColor = split[0];
            this.bgAlpha = split[1];
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsQuestion() {
        return isQuestion;
    }

    public void setIsQuestion(String isQuestion) {
        this.isQuestion = isQuestion;
    }

    public List<SubviewData> getSubviews() {
        return subviews;
    }

    public void setSubviews(List<SubviewData> subviews) {
        this.subviews = subviews;
    }

    @Override
    public String toString() {
        return "ViewData{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", bold='" + bold + '\'' +
                ", front='" + front + '\'' +
                ", textColor='" + textColor + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", bgAlpha='" + bgAlpha + '\'' +
                ", url='" + url + '\'' +
                ", isQuestion='" + isQuestion + '\'' +
                ", subviews=" + subviews +
                '}';
    }
}
