package com.uratio.testdemo.designmodel.visitor;

import java.util.Random;

public abstract class Students {
    public String name;
    public int totalScore; // 总成绩
    Students(String aName) {
        name = aName;
        totalScore = new Random().nextInt(100);
    }
//    public abstract void accept(Visitor visitor);
}