package com.vanjav.sproots;

import java.util.Random;

public class Sproot {
    private PointF position;
    private Random random;

    public Sproot(PointF position) {
        this.position = position;
        random = new Random();
    }

    public PointF getPosition() {
        return position;
    }

    public void update() {
        position.offset(1, 0);
    }
}
