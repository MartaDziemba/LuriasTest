package com.example.marta.luriatest;

public class PointXY {
    private float x;
    private float y;

    public PointXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PointXY() {

    }

    public double getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "PointXY{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
