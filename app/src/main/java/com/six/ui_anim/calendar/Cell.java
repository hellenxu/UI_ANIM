package com.six.ui_anim.calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * @author hellenxu
 * @date 2015/9/6
 * Copyright 2015 com.six. All rights reserved.
 */
public class Cell {
    private static final String TAG = Cell.class.getSimpleName();
    //    private boolean isBold;
    private int mDayOfMonth; // range between 1~31
    private Rect mBound;
    private float mTextSize = 26;
    protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
    private int xStartPoint;
    private int yStartPoint;

    public Cell(int dayOfMonth, Rect rect, float textSize, boolean isBold) {
        if (dayOfMonth > 31 || dayOfMonth < 1)
            throw new IllegalArgumentException("dayOfMonth should range between 1 and 31");
        if (rect == null)
            throw new IllegalArgumentException("rect or paint null");

        mDayOfMonth = dayOfMonth;
        mBound = new Rect(rect);

        mTextSize = textSize;
        mPaint.setColor(Color.BLACK);
        mPaint.setFakeBoldText(isBold);
        mPaint.setTextSize(textSize);

        xStartPoint = mBound.centerX() - (int) (mPaint.measureText(String.valueOf(mDayOfMonth)) / 2);
        yStartPoint = mBound.centerY() + (int) ((mPaint.descent() - mPaint.ascent()) / 2);

    }

    public Cell(int dayOfMonth, Rect rect, float textSize) {
        this(dayOfMonth, rect, textSize, false);
    }

    public int getDayOfMonth() {
        return mDayOfMonth;
    }

    public boolean hitCell(int x, int y) {
        return mBound.contains(x, y);
    }

    public Rect getBound() {
        return mBound;
    }

    public float getTextSize() {
        return mTextSize;
    }

    protected void draw(Canvas canvas) {
        canvas.drawText(String.valueOf(mDayOfMonth), xStartPoint, yStartPoint, mPaint);
    }

    @Override
    public String toString() {
        return String.valueOf(mDayOfMonth) + "(" + mBound.toString() + ")";
    }
}
