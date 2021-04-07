package com.uratio.testdemo.parse.msg;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author lang
 * @data 2021/4/7
 */
public class SubviewData implements Serializable {
    @XmlElement(name = Constants.XmlKey.VIEW)
    private List<ViewData> views;

    public List<ViewData> getViews() {
        return views;
    }

    public void setViews(List<ViewData> views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "SubviewData{" +
                "views=" + views +
                '}';
    }
}
