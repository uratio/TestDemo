package com.uratio.testdemo.hexagon;

/**
 * @author lang
 * @data 2020/9/9
 */
public class Point {
    private float x;
    private float y;

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
