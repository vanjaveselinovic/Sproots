package com.vanjav.sproots;

public class PointF {
    public float x;
    public float y;

    public PointF(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PointF(PointF pointF) {
        this.x = pointF.x;
        this.y = pointF.y;
    }

    public void offset(float x, float y) {
        this.x += x;
        this.y += y;
    }
}
