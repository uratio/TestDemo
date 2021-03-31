package com.uratio.testdemo.thread;

import androidx.annotation.NonNull;

/**
 * @author lang
 * @data 2021/2/22
 */
public class TestPrint {
    private int num = 1;
    private int state = 1;

    public void printNum() {
        new Thread1("Thread1").start();
        new Thread(new Thread2(), "Thread2").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (num <= 100) {
                    if (state == 3) {
                        System.out.println(Thread.currentThread().getName() + "：" + num++);
                        state = 1;
                    }
                }
            }
        }, "Thread3").start();
    }

    class Thread1 extends Thread{

        public Thread1(@NonNull String name) {
            super(name);
        }

        @Override
        public void run() {
            super.run();
            while (num <= 100) {
                if (state == 1) {
                    System.out.println(Thread.currentThread().getName() + "：" + num++);
                    state = 2;
                }
            }
        }
    }

    class Thread2 implements Runnable{
        @Override
        public void run() {
            while (num <= 100) {
                if (state == 2) {
                    System.out.println(Thread.currentThread().getName() + "：" + num++);
                    state = 3;
                }
            }
        }
    }
}
