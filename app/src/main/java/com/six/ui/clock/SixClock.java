package com.six.ui.clock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.six.ui.R;


/**
 * @author hellenxu
 * @date 2016/3/8
 * Copyright 2016 Six. All rights reserved.
 */
public class SixClock extends View {
    private Paint circlePaint;
    private Paint scalePaint;
    private Paint needlePaint;
    private int width;
    private int height;
    private int centerX;
    private int centerY;
    private int radius;
    private int minRadius;
    private int hourRadius;
    private int circleRadius;
    private int scaleWidth;
    private int circleWidth;
    private int minWidth;
    private int hourWidth;

    private static final int DEFAULT_MIN_RADIUS = 80;
    private static final int DEFAULT_HOUR_RADIUS = 50;
    private static final int DEFAULT_CIRCLE_RADIUS = 200;
    private static final int DEFAULT_X = 100;
    private static final int DEFAULT_SCALE_WIDTH = 3;
    private static final int DEFAULT_CIRCLE_WIDTH = 5;
    private static final int DEFAULT_MIN_WIDTH = 10;
    private static final int DEFAULT_HOUR_WIDTH = 20;
    private static final int DEFAULT_SCALE_OFFSET_Y = 60;
    private static final int DEFAULT_SCALE_TEXT_OFFSET_Y = 90;

    public SixClock(Context context) {
        this(context, null);
    }

    public SixClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SixClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SixClock);
        minRadius = ta.getDimensionPixelSize(R.styleable.SixClock_min_radius, DEFAULT_MIN_RADIUS);
        hourRadius = ta.getDimensionPixelSize(R.styleable.SixClock_hour_radius, DEFAULT_HOUR_RADIUS);
        scaleWidth = ta.getDimensionPixelSize(R.styleable.SixClock_scale_width, DEFAULT_SCALE_WIDTH);
        circleWidth = ta.getDimensionPixelSize(R.styleable.SixClock_circle_width, DEFAULT_CIRCLE_WIDTH);
        minWidth = ta.getDimensionPixelSize(R.styleable.SixClock_min_width, DEFAULT_MIN_WIDTH);
        hourWidth = ta.getDimensionPixelSize(R.styleable.SixClock_hour_width, DEFAULT_HOUR_WIDTH);
        circleRadius = ta.getDimensionPixelSize(R.styleable.SixClock_circle_radius, DEFAULT_CIRCLE_RADIUS);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width, height;
        int widMode = MeasureSpec.getMode(widthMeasureSpec);
        int meaWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewWidth = getPaddingLeft() + getPaddingRight() + (circleRadius + circleWidth) * 2;
        switch (widMode){
            case MeasureSpec.EXACTLY:
                width = viewWidth;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(meaWidth, viewWidth);
                break;
            default:
                width = meaWidth;
                break;
        }

        int meaHeight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int viewHeight = getPaddingTop() + getPaddingBottom() + (circleRadius + circleWidth) * 2;
        switch (heightMode){
            case MeasureSpec.EXACTLY:
                height = viewHeight;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(meaHeight, viewHeight);
                break;
            default:
                height = meaHeight;
                break;
        }
        setMeasuredDimension(width, height);
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(circleWidth);

        scalePaint = new Paint(circlePaint);
        scalePaint.setStrokeWidth(scaleWidth);

        needlePaint = new Paint(circlePaint);
        needlePaint.setStrokeWidth(hourWidth);

        width = getWidth();
        height = getHeight();
        centerX = width / 2;
        centerY = height / 2;
        radius = Math.min(circleRadius, Math.min(width, height) / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, radius, circlePaint);

        for (int i = 0; i < 13; i++) {
            if (i == 3 || i == 6 || i == 9 || i == 12) {
                scalePaint.setStrokeWidth(5);
                scalePaint.setTextSize(30);
                canvas.drawLine(centerX, centerY - radius, centerX, centerY - radius + DEFAULT_SCALE_OFFSET_Y, scalePaint);
                String degree = String.valueOf(i);
                canvas.drawText(degree, centerX - scalePaint.measureText(degree) / 2, centerY - radius + DEFAULT_SCALE_TEXT_OFFSET_Y, scalePaint);
            } else {
                scalePaint.setStrokeWidth(3);
                scalePaint.setTextSize(15);
                canvas.drawLine(centerX, centerY - radius, centerX, centerY - radius + 30, scalePaint);
                String degree = String.valueOf(i);
                if (!TextUtils.equals(degree, "0")) {
                    canvas.drawText(degree, centerX - scalePaint.measureText(degree) / 2, centerY - radius + 60, scalePaint);
                }
            }
            canvas.rotate(30, centerX, centerY);
        }

        canvas.save();

        canvas.translate(centerX, centerY);
        canvas.drawLine(0, 0, DEFAULT_X, DEFAULT_HOUR_RADIUS, needlePaint);
        needlePaint.setStrokeWidth(minWidth);
        canvas.drawLine(0, 0, DEFAULT_X, DEFAULT_MIN_RADIUS, needlePaint);
        canvas.restore();
    }
}
