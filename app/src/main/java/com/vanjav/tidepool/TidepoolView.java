package com.vanjav.tidepool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ResourceBundle;

/**
 * Created by vveselin on 04/10/2016.
 */

public class TidepoolView extends SurfaceView implements Choreographer.FrameCallback {
    private long previousFrameNanos;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private int width, height;
    private Controller controller;
    private Paint paintSand, paintSandDark, paintWater, paintItem, paintFish;
    Path path;

    public TidepoolView(Context context) {
        this(context, null);
    }

    public TidepoolView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintWater = new Paint();
        paintWater.setColor(ContextCompat.getColor(getContext(), R.color.colorWater));
        paintSand = new Paint();
        paintSand.setColor(ContextCompat.getColor(getContext(), R.color.colorSand));
        paintSandDark = new Paint();
        paintSandDark.setColor(ContextCompat.getColor(getContext(), R.color.colorSandDark));
        paintItem = new Paint();
        paintItem.setColor(ContextCompat.getColor(getContext(), R.color.colorItem));
        paintFish = new Paint();
        paintFish.setColor(ContextCompat.getColor(getContext(), R.color.colorFish));

        path = new Path();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        controller = new Controller();
        controller.addPool(new Pool(width/2, 300, 200, 200));
        controller.addPool(new Pool(width/2, 650, 0, 200));
        controller.addPool(new Pool(width/2, 1000, 200, 200));
        controller.addItem(new Item(width/2, 825));
        controller.addFish(new Fish(controller.getPools().get(2)));

        surfaceHolder = getHolder();

        previousFrameNanos = System.nanoTime();

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        Choreographer.getInstance().postFrameCallback(this);

        update((frameTimeNanos - previousFrameNanos)/1000000);
        draw();

        previousFrameNanos = frameTimeNanos;
    }

    private void update(long deltaTimeNanos) {
        controller.update(deltaTimeNanos);
    }

    private void draw() {
        try {
            canvas = surfaceHolder.lockCanvas(null);
            if(canvas != null){
                synchronized (surfaceHolder) {
                    canvas.drawRect(0, 0, width, height, paintSand);
                    for (Pool pool : controller.getPools()) {
                        canvas.drawCircle(pool.getX(), pool.getY(), pool.getRInit(), paintSandDark);
                    }
                    for (Pool pool : controller.getPools()) {
                        canvas.drawCircle(pool.getX(), pool.getY(), pool.getR(), paintWater);
                        if (pool.isDraining() == 1) {
                            path.reset();
                            path.addCircle(pool.getX(), pool.getY(), pool.getRInit(), Path.Direction.CW);
                            canvas.clipPath(path);
                            canvas.drawCircle(pool.getFillX(), pool.getFillY(), pool.getRInit(), paintWater);
                            canvas.clipRect(new Rect(0, 0, width, height), Region.Op.UNION);
                        }
                    }
                    canvas.drawRect(0, 0, width, 200, paintWater);
                    for (Item item : controller.getItems()) {
                        canvas.drawCircle(item.getX(), item.getY(), item.getR(), paintItem);
                    }
                    canvas.drawCircle(controller.getFish().getX(), controller.getFish().getY(), controller.getFish().getR(), paintFish);
                }
            }
        }
        finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        controller.touch((int) event.getX(), (int) event.getY());
        return true;
    }
}
