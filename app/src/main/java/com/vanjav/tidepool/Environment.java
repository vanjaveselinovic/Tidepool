package com.vanjav.tidepool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by vveselin on 04/10/2016.
 */

public class Environment extends View {
    private Paint paint;
    private int r = 200;
    private int fps = 60;
    private int drainTime = 500; //0.5 seconds

    public Environment (Context context) {
        super(context);
        setup();
    }

    public Environment (Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public Environment (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    private void setup() {
        paint = new Paint();
        paint.setColor(Color.CYAN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.YELLOW);
        canvas.drawCircle(getWidth()/2, getHeight()/2, r, paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getX() > getWidth()/2 - r && event.getX() < getWidth()/2 + r && event.getY() > getHeight()/2 - r && event.getY() < getHeight()/2 + r) {
            r -= 1;
            ((Environment) findViewById(R.id.environment)).invalidate();
        }

        return true;
    }
}
