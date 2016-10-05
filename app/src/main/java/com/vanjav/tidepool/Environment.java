package com.vanjav.tidepool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by vveselin on 04/10/2016.
 */

public class Environment extends SurfaceView implements Choreographer.FrameCallback {
    private Paint paint;
    private int r = 200;
    private boolean drain = false;
    private int drainTime = 500;

    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public Environment (Context context) {
        this(context, null);
    }

    public Environment (Context context, AttributeSet attrs) {
        super(context, attrs);

        surfaceHolder = getHolder();
        paint = new Paint();
        paint.setColor(Color.CYAN);

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        Choreographer.getInstance().postFrameCallback(this);
        update();
    }
    private void update() {
        if (drain) {
            r -= drainTime/17;
            if (r < 0) {
                drain = false;
                r = 200;
            }
        }

        try {
            canvas = surfaceHolder.lockCanvas(null);
            if(canvas != null){
                synchronized (surfaceHolder) {
                    canvas.drawColor(Color.YELLOW);
                    canvas.drawCircle(getWidth()/2, getHeight()/2, r, paint);
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
        if (event.getX() > getWidth()/2 - r && event.getX() < getWidth()/2 + r && event.getY() > getHeight()/2 - r && event.getY() < getHeight()/2 + r) {
            drain = true;
        }

        return true;
    }
}
