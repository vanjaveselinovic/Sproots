package com.vanjav.sproots;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class Sproot {
    private PointF position;
    private LinkedList<PointF> last100Positions;
    private Random random = new Random();

    private int currDirection = 0;

    private int numLeaves = 1;

    public Sproot(PointF position) {
        this.position = position;
        this.last100Positions = new LinkedList<PointF>(Collections.nCopies(100, position));
    }

    public PointF getPosition() {
        return position;
    }

    public int getCurrDirection() {
        return currDirection;
    }

    public int getNumLeaves() {
        return numLeaves;
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
        last100Positions.addFirst(new PointF(position));
        last100Positions.removeLast();
    }

    public void addLeaf() {
        numLeaves++;
    }

    public ListIterator<PointF> getPositionsIterator() {
        return last100Positions.listIterator();
    }
}
