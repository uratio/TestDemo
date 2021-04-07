package com.uratio.testdemo.parse;

public class Row {

    private String first;
    private String second;

    public Row() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "first=" + first + ",second=" + second;
    }

}