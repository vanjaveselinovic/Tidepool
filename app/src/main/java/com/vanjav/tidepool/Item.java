package com.vanjav.tidepool;

/**
 * Created by vveselin on 06/10/2016.
 */

public class Item {
    private int x, y, r;

    public Item (int x, int y) {
        this.x = x;
        this.y = y;
        this.r = 100;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }
}
