package com.uratio.testdemo.designmodel.agency;

/**
 * @author lang
 * @data 2021/3/2
 */
public class StaticProxy implements ProxyInterface {
    private Peter peter;

    public StaticProxy(Peter peter) {
        this.peter = peter;
    }

    @Override
    public void choiceBetterHouse() {
        peter.choiceBetterHouse();
    }

    @Override
    public void buyHouse() {
        peter.buyHouse();
    }
}
