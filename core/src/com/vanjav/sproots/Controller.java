package com.vanjav.sproots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {
    private int width, height;
    private float dpi;

    private float sprootWidth;

    private Random random;

    private List<Sproot> sproots;

    public Controller(
            int width,
            int height,
            float density,
            float sprootWidth
    ) {
        this.width = width;
        this.height = height;
        this.dpi = density * 160f;
        this.sprootWidth = sprootWidth;

        random = new Random();

        sproots = new ArrayList<Sproot>();

        sproots.add(new Sproot(new PointF(100, 100)));
    }

    public List<Sproot> getSproots() {
        return sproots;
    }

    private int s;

    public void update(float deltaTimeSeconds) {
        for (s = 0; s < sproots.size(); s++) {
            sproots.get(s).update();
        }
    }
}
