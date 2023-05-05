package com.uratio.testdemo.utils;

import java.util.Stack;

/**
 * @author lang
 * @data 2021/1/29
 */
public class MyQueue {
    private Stack<Integer> in = new Stack<>(), out = new Stack<>();

    //定义一个辅助函数来处理当 out 为空时，将 in 里面的数据挪到 out 中去
    private void transferIfEmpty() {
        if (out.empty()) {
            while (!in.empty()) {
                out.push(in.pop());
            }
        }
    }

    public MyQueue() {
    }

    /**
     * 将一个元素放入队列的尾部
     */
    public void push(int x) {
        in.push(x);
    }

    /**
     * 从队列首部移除元素
     */
    public int pop() {
        transferIfEmpty();
        return out.pop();
    }

    /**
     * 返回队列首部的元素
     */
    public int peek() {
        transferIfEmpty();
        return out.peek();
    }

    /**
     * 返回队列是否为空
     */
    public boolean empty() {
        return in.empty() && out.empty();
    }
}
