package com.vanjav.sproots;

import java.util.Random;

public class Sproot {
    private PointF position;
    private Random random;

    private int currDirection = 0;

    public Sproot(PointF position) {
        this.position = position;
        random = new Random();
    }

    public PointF getPosition() {
        return position;
    }

    public void update() {
        if (currDirection == 0) {
            if (random.nextFloat() > 0.99) {
                currDirection = random.nextFloat() < 0.5 ? -1 : 1;
            }
        }
        else {
            if (random.nextFloat() > 0.99) {
                currDirection = random.nextFloat() < 0.5 ? 0 : currDirection * -1;
            }
        }
        position.offset(currDirection, 0);
    }
}
