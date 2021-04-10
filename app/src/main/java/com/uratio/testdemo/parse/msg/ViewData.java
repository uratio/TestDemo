package com.uratio.testdemo.parse.msg;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author lang
 * @data 2021/4/7
 */
public class ViewData implements Serializable {
    @XmlElement(name = XmlConfig.XmlKey.TYPE)
    private String type;
    @XmlElement(name = XmlConfig.XmlKey.TEXT)
    private String text;
    @XmlElement(name = XmlConfig.XmlKey.BOLD)
    private int bold;
    @XmlElement(name = XmlConfig.XmlKey.FRONT)
    private float front;
    @XmlElement(name = XmlConfig.XmlKey.TEXT_COLOR)
    private String textColor;
    private float textColorAlpha;
    @XmlElement(name = XmlConfig.XmlKey.BACKGROUND_COLOR)
    private String backgroundColor;
    private float bgAlpha;
    @XmlElement(name = XmlConfig.XmlKey.URL)
    private String url;
    @XmlElement(name = XmlConfig.XmlKey.IS_QUESTION)
    private int isQuestion;
    @XmlElement(name = XmlConfig.XmlKey.SUBVIEWS)
    private SubviewData subviews;

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

    public int getBold() {
        return bold;
    }

    public void setBold(String bold) {
        this.bold = Integer.parseInt(bold);
    }

    public float getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = Float.parseFloat(front);
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        String[] split = textColor.split(",");
        if (split.length == 1) {
            this.textColor = "#" + textColor;
        } else if (split.length == 2) {
            this.textColor = split[0];
            this.textColorAlpha = Float.parseFloat(split[1]);
        }
    }

    public float getTextColorAlpha() {
        return textColorAlpha;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        String[] split = backgroundColor.split(",");
        if (split.length == 1) {
            this.backgroundColor = "#" + backgroundColor;
        } else if (split.length == 2) {
            this.backgroundColor = split[0];
            this.bgAlpha = Float.parseFloat(split[1]);
        }
    }

    public float getBgAlpha() {
        return bgAlpha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsQuestion() {
        return isQuestion;
    }

    public void setIsQuestion(String isQuestion) {
        this.isQuestion = Integer.parseInt(isQuestion);
    }

    public SubviewData getSubviews() {
        return subviews;
    }

    public void setSubviews(SubviewData subviews) {
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
