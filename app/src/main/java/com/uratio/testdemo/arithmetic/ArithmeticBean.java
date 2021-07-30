package com.uratio.testdemo.arithmetic;

import java.io.Serializable;

/**
 * @author lang
 * @data 2021/7/23
 */
public class ArithmeticBean implements Serializable {
    public String name;
    public String type;

    public ArithmeticBean(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
