package com.vanjav.tidepool;

/**
 * Created by vveselin on 06/10/2016.
 */

public class Fish {
    private int x, y, r;
    private Pool pool, destPool;
    private boolean swim;

    public Fish(Pool pool) {
        this.x = pool.getX();
        this.y = pool.getY();
        r = 30;
        this.pool = pool;
        swim = false;
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

    public Pool getPool() {
        return pool;
    }

    public Pool getDestPool() {
        return destPool;
    }

    public void setDestPool(Pool pool) {
        destPool = pool;
    }

    public void swim(boolean swim) {
        this.swim = swim;
    }

    public boolean isSwimming () {
        return swim;
    }

    public void setX (int x) {
        this.x = x;
    }

    public void setY (int y) {
        this.y = y;
    }
}
