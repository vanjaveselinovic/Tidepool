package com.vanjav.tidepool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ResourceBundle;

/**
 * Created by vveselin on 04/10/2016.
 */

public class TidepoolView extends SurfaceView implements Choreographer.FrameCallback {
    long previousFrameNanos;

    private Paint paint;

    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private Controller controller;

    public TidepoolView(Context context) {
        this(context, null);
    }

    public TidepoolView(Context context, AttributeSet attrs) {
        super(context, attrs);

        controller = new Controller();

        surfaceHolder = getHolder();
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorWater));

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
        controller.updatePools(deltaTimeNanos);
    }

    private void draw() {
        try {
            canvas = surfaceHolder.lockCanvas(null);
            if(canvas != null){
                synchronized (surfaceHolder) {
                    canvas.drawColor(ContextCompat.getColor(getContext(), R.color.colorSand));
                    canvas.drawRect(0,0,getWidth(),200,paint);
                    for (Pool pool : controller.getPools()) {
                        canvas.drawCircle(pool.getX(), pool.getY(), pool.getR(), paint);
                    }
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
