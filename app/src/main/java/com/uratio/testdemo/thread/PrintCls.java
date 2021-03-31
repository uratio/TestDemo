package com.uratio.testdemo.thread;

/**
 * @author lang
 * @data 2021/2/22
 */
public class PrintCls {
    public static int num = 1;
    public static int state = 1;

    public void printNum(String tag) {
        synchronized (PrintCls.class) {
            int mTag = Integer.parseInt(tag);
            while (mTag != state) {
                try {
                    PrintCls.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "：" + num++);
            }
            state = state % 3 + 1;
            PrintCls.class.notifyAll();
        }
    }
}
