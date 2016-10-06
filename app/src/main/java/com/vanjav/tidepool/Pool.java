package com.vanjav.tidepool;

/**
 * Created by vveselin on 05/10/2016.
 */

public class Pool {
    private int x, y, r, rInit;
    private int draining;

    public Pool(int x, int y, int r, int rInit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.rInit = rInit;
        draining = 0;
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

    public int getRInit() {
        return rInit;
    }

    public int isDraining() {
        return draining;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void drain(int draining) {
        this.draining = draining;
    }
}
