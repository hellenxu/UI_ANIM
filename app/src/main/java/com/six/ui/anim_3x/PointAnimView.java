package com.six.ui.anim_3x;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author hellenxu
 * @date 2015/10/15
 * Copyright 2015 Six. All rights reserved.
 */
public class PointAnimView extends View {
    public static final float RADIUS = 50f;
    private Point currentPoint;
    private Paint mPaint;
    private String color;

    public PointAnimView(Context context) {
        this(context, null);
    }

    public PointAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    private void startAnimation() {
//        Point startPoint = new Point(RADIUS, RADIUS);
//        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        Point startPoint = new Point(getWidth() / 2, RADIUS);
        Point endPoint = new Point(getWidth() / 2, getHeight() - RADIUS);
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });

//        anim.setInterpolator(new AccelerateInterpolator(2f));
//        anim.setInterpolator(new BounceInterpolator());
        anim.setInterpolator(new DecelerateAccelerateInterpolator());
        anim.setDuration(3000);
        anim.start();

//        ObjectAnimator objAnim = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(), "#0000FF", "#FF0000");
//        objAnim.setDuration(5000);

//        AnimatorSet animSet = new AnimatorSet();
//        animSet.playTogether(anim, objAnim);
//        animSet.setDuration(5000);
//        animSet.start();

        Log.d("PointAnimView", "cos1: " + Math.cos(Math.PI));
        Log.d("PointAnimView", "cos2: " + Math.cos(2 * Math.PI));
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }
}
