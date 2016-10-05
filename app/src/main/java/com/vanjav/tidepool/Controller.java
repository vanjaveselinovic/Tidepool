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
        for (Pool pool : pools) {
            if (!pool.isDraining() && x > pool.getX() - pool.getR() && x < pool.getX() + pool.getR() && y > pool.getY() - pool.getR() && y < pool.getY() + pool.getR()) {
                pool.drain(true);
            }
        }

        addPool(new Pool(x+200, y+200, 200));
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
