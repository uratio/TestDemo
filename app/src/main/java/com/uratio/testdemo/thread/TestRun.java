package com.uratio.testdemo.thread;

/**
 * @author lang
 * @data 2021/2/22
 */
public class TestRun implements Runnable {
    private int count = 0;

    @Override
    public void run() {
        while (count < 3) {
            count++;
            PrintCls printCls = new PrintCls();
            printCls.printNum(Thread.currentThread().getName());
        }
    }
}
