package com.vanjav.tidepool;

import java.util.ArrayList;

/**
 * Created by vveselin on 05/10/2016.
 */

public class Controller {
    private ArrayList<Pool> pools;
    private int drainTime;

    public Controller () {
        pools = new ArrayList<Pool>();
        drainTime = 500;
    }

    public void touch(int x, int y) {
        boolean touchedPool = false;

        for (Pool pool : pools) {
            if (x > pool.getX() - pool.getR() && x < pool.getX() + pool.getR() && y > pool.getY() - pool.getR() && y < pool.getY() + pool.getR()) {
                if (!pool.isDraining())
                    pool.drain(true);
                touchedPool = true;
            }
        }
    }

    public void updatePools(float deltaTimeNanos) {
        for (Pool pool : pools) {
            if (pool.isDraining()) {
                pool.setR((int) (pool.getR() - pool.getRInit()*(deltaTimeNanos/drainTime)));
                if (pool.getR() < 0) {
                    pool.drain(false);
                }
            }
        }
    }

    public ArrayList<Pool> getPools () {
        return pools;
    }

    public void addPool (Pool pool) {
        pools.add(pool);
    }
}
