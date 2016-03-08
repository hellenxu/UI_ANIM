package com.six.sixua.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.six.sixua.R;

/**
 * @author hellenxu
 * @date 2016/3/8
 * Copyright 2016 Six. All rights reserved.
 */
public class SixProgressWheel extends View {
    private Paint mBarPaint;
    private Paint mRimPaint;
    private float mBarWidth;
    private float mRimWidth;
    private int mBarColor;
    private int mRimColor;
    private float mSpinSpeed;
    private float mRadius;
    private boolean isLoading = true;
    private float mCircleX;
    private float mCircleY;
    private RectF mWheelRectF;

    private static final int DEFAULT_BAR_WIDTH = 3;
    private static final int DEFAULT_RIM_WIDTH = 3;
    private static final int DEFAULT_BAR_COLOR = 0xAA000000;
    private static final int DEFAULT_RIM_COLOR = 0x00FFFFFF;
    private static final float DEFAULT_SPEED = 100;
    private static final float DEFAULT_RADIUS = 300;

    public SixProgressWheel(Context context) {
        this(context, null);
    }

    public SixProgressWheel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SixProgressWheel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttrs(attrs, context);
        initPaint();
    }

    private void parseAttrs(AttributeSet attrs, Context context) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SixWheel);
        mBarWidth = ta.getDimensionPixelSize(R.styleable.SixWheel_six_bar_width, DEFAULT_BAR_WIDTH);
        mRimWidth = ta.getDimensionPixelSize(R.styleable.SixWheel_six_rim_width, DEFAULT_RIM_WIDTH);
        mBarColor = ta.getColor(R.styleable.SixWheel_six_bar_color, DEFAULT_BAR_COLOR);
        mRimColor = ta.getColor(R.styleable.SixWheel_six_rim_color, DEFAULT_RIM_COLOR);
        mSpinSpeed = ta.getDimension(R.styleable.SixWheel_spinning_speed, DEFAULT_SPEED);
        mRadius = ta.getDimension(R.styleable.SixWheel_six_radius, DEFAULT_RADIUS);
        ta.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCircleX = w / 2;
        mCircleY = h / 2;
        setWheelRectF(w, h);
    }

    private void initPaint() {
        mBarPaint = new Paint();
        mBarPaint.setAntiAlias(true);
        mBarPaint.setStyle(Paint.Style.STROKE);
        mBarPaint.setColor(mBarColor);
        mBarPaint.setStrokeWidth(mBarWidth);

        mRimPaint = new Paint();
        mRimPaint.setAntiAlias(true);
        mRimPaint.setStyle(Paint.Style.STROKE);
        mRimPaint.setColor(mRimColor);
        mRimPaint.setStrokeWidth(mRimWidth);
    }

    /**
     * reset the width and height to make sure the size of view is compatible with the radius size.
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height, width;

        int meaWidth = MeasureSpec.getSize(widthMeasureSpec);
        int meaHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int viewWidth = (int) (getPaddingLeft() + getPaddingRight() + (mRadius + mBarWidth) * 2);
        int viewHeight = (int) (getPaddingTop() + getPaddingBottom() + (mRadius + mBarWidth) * 2);

        switch (widthMode){
            case MeasureSpec.EXACTLY: //e.g.: match_parent, 100dp
                width = viewWidth;
                break;
            case MeasureSpec.AT_MOST: //e.g.: wrap_content
                width = Math.min(meaWidth, viewWidth);
                break;
            default:
                width = meaWidth;
                break;
        }

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

    private void setWheelRectF(int width, int height) {
        int slimWidth = width - getPaddingLeft() - getPaddingRight();
        int slimHeight = height - getPaddingTop() - getPaddingBottom();

        float minValue = Math.min(Math.min(slimWidth, slimHeight), (mRadius - mBarWidth) * 2);
        float left = (slimWidth - minValue) / 2 - mBarWidth;
        float top = (slimHeight - minValue) / 2 - mBarWidth;
        Log.d("setWheelRectF", "left: " + left + "; top: " + top);
        mWheelRectF = new RectF(left, top, left + mRadius, top + mRadius);
    }

    //TODO
    /**
     * two steps: 1) draw a circle using mRimPaint; 2) draw loading progress bar.
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //step 1: draw the rim
        canvas.drawArc(mWheelRectF, 0, 360, true, mRimPaint);
        if(isLoading){

            canvas.drawArc(mWheelRectF,0, 0, false, mBarPaint);
        }

    }

    //TODO
    private void updateProgress(){

    }
}
