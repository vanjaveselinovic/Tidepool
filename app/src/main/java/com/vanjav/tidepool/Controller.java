package com.vanjav.tidepool;

import java.util.ArrayList;

/**
 * Created by vveselin on 05/10/2016.
 */

public class Controller {
    private ArrayList<Pool> pools;
    private ArrayList<Item> items;
    private Fish fish;

    private int drainTime, swimTime;

    public Controller () {
        pools = new ArrayList<Pool>();
        items = new ArrayList<Item>();
        drainTime = 2000;
        swimTime = 2000;
    }

    public void addFish (Fish fish) {
        this.fish = fish;
    }

    public void touch(int x, int y) {
        /*
        boolean touchedPool = false;

        for (Pool pool : pools) {
            if (x > pool.getX() - pool.getR() && x < pool.getX() + pool.getR() && y > pool.getY() - pool.getR() && y < pool.getY() + pool.getR()) {
                if (!pool.isDraining())
                    pool.drain(true);
                touchedPool = true;
            }
        }
        */

        for (Item item : items) {
            if (x > item.getX() - item.getR() && x < item.getX() + item.getR() && y > item.getY() - item.getR() && y < item.getY() + item.getR()) {
                items.remove(item);
                pools.get(1).setFillSource(pools.get(2));
                pools.get(1).drain(1);
                pools.get(2).drain(-1);
                fish.setDestPool(pools.get(1));
                fish.swim(true);
            }
        }
    }

    public void update(float deltaTimeNanos) {
        for (Pool pool : pools) {
            if (pool.isDraining() == 1) {
                /*pool.setR((int) (pool.getR() + pool.getRInit()*(deltaTimeNanos/drainTime)));
                if (pool.getR() >= pool.getRInit()) {
                    pool.setR(pool.getRInit());
                    pool.drain(0);
                }*/
                pool.setFillX((int) (pool.getFillX() + (pool.getX() - pool.getFillSource().getX())*(deltaTimeNanos/drainTime)));
                pool.setFillY((int) (pool.getFillY() + (pool.getY() - pool.getFillSource().getY())*(deltaTimeNanos/drainTime)));
                if (Math.abs(pool.getFillX() - pool.getFillSource().getX()) >= Math.abs(pool.getX() - pool.getFillSource().getX())
                        && Math.abs(pool.getFillY() - pool.getFillSource().getY()) >= Math.abs(pool.getY() - pool.getFillSource().getY())) {
                    pool.setR(pool.getRInit());
                    pool.drain(0);
                }
            }
            else if (pool.isDraining() == -1) {
                pool.setR((int) (pool.getR() - pool.getRInit()*(deltaTimeNanos/drainTime)));
                if (pool.getR() <= 0) {
                    pool.setR(0);
                    pool.drain(0);
                }
            }
        }
        if (fish.isSwimming()) {
            fish.setX((int) (fish.getX() + (fish.getDestPool().getX() - fish.getPool().getX())*(deltaTimeNanos / swimTime)));
            fish.setY((int) (fish.getY() + (fish.getDestPool().getY() - fish.getPool().getY())*(deltaTimeNanos / swimTime)));
            if (Math.abs(fish.getX() - fish.getPool().getX()) >= Math.abs(fish.getDestPool().getX() - fish.getPool().getX())
                    && Math.abs(fish.getY() - fish.getPool().getY()) >= Math.abs(fish.getDestPool().getY() - fish.getPool().getY())) {
                fish.setX(fish.getDestPool().getX());
                fish.setY(fish.getDestPool().getY());
                fish.setDestPool(fish.getDestPool());
                fish.swim(false);
            }
        }
    }

    public ArrayList<Pool> getPools () {
        return pools;
    }

    public ArrayList<Item> getItems () {
        return items;
    }

    public Fish getFish () {
        return fish;
    }

    public void addPool (Pool pool) {
        pools.add(pool);
    }

    public void addItem (Item item) {
        items.add(item);
    }
}
