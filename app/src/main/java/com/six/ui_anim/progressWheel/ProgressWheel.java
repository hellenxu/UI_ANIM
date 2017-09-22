package com.six.ui_anim.progressWheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.six.ui_anim.R;

/**
 * @author hellenxu
 * @date 2015/9/1
 * Copyright 2015 six.com. All rights reserved.
 */

public class ProgressWheel extends View {

    //dimension unit: dip
    private int mBarWidth;
    private int mRimWidth;

    private int mBarColor;
    private int mRimColor;
    private float mSpinSpeed;//speed unit: angles
    private Paint mBarPaint;
    private Paint mRimPaint;
    private boolean isSpinning = true;
    private float mProgress; //unit: angles
    private float mTargetProgress = 0.0f;
    private int mWheelRadius;
    private long mLastAnimationTime;
    private RectF mWheelBounds = new RectF();
    private long pausedTimeWithoutGrowing;
    private final long pauseGrowingTime = 200;
    private double timeStartGrowing = 0;
    private double barSpinCycleTime = 460;
    private boolean barGrowingFromFront = true;
    private float barExtraLength = 0;

    private boolean isLoadingFinished;
    private boolean isFirstLine;
    private boolean isSecondLine;
    private int mTickSize;//钩钩的size
    private float mLineSpeed = 0.0f;

    private final int DEFAULT_BAR_WIDTH = 3;
    private final int DEFAULT_RIM_WIDTH = 3;
    private final int DEFAULT_BAR_COLOR = 0xAA000000;
    private final int DEFAULT_RIM_COLOR = 0x00FFFFFF;
    private final float DEFAULT_SPIN_SPEED = 100.0f;
    private final int DEFAULT_RADIUS = 30;
    private final int DEFAULT_TICK_SIZE = 25;

    //unite: angles
    private final int INIT_BAR_LENGTH = 16;
    private final int MAX_BAR_LENGTH = 270;

    public ProgressWheel(Context context) {
        this(context, null);
    }

    public ProgressWheel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressWheel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs);
        initPaints();
    }

    private void parseAttributes(Context ctx, AttributeSet attrs) {
        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        TypedArray ta = ctx.getResources().obtainAttributes(attrs, R.styleable.mat_progress);

        mBarWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ta.getDimension(R.styleable.mat_progress_bar_width, DEFAULT_BAR_WIDTH), metrics);
        mWheelRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ta.getDimension(R.styleable.mat_progress_mat_radius, DEFAULT_RADIUS), metrics);
        mRimWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ta.getDimension(R.styleable.mat_progress_rim_width, DEFAULT_RIM_WIDTH), metrics);
        mTickSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ta.getDimension(R.styleable.mat_progress_tick_size, DEFAULT_TICK_SIZE), metrics);
        mBarColor = ta.getColor(R.styleable.mat_progress_bar_color, DEFAULT_BAR_COLOR);
        mRimColor = ta.getColor(R.styleable.mat_progress_rim_color, DEFAULT_RIM_COLOR);
        mSpinSpeed = ta.getFloat(R.styleable.mat_progress_spin_speed, DEFAULT_SPIN_SPEED);

        ta.recycle();
    }

    private void initPaints() {
        mBarPaint = new Paint();
        mBarPaint.setAntiAlias(true);
        mBarPaint.setColor(mBarColor);
        mBarPaint.setStyle(Style.STROKE);
        mBarPaint.setStrokeWidth(mBarWidth);
        mBarPaint.setStrokeCap(Paint.Cap.ROUND);//后圆角
        mBarPaint.setStrokeJoin(Paint.Join.ROUND);//前圆角

        mRimPaint = new Paint();
        mRimPaint.setAntiAlias(true);
        mRimPaint.setColor(mRimColor);
        mRimPaint.setStyle(Style.STROKE);
        mRimPaint.setStrokeWidth(mRimWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setWheelBounds(w, h);
        initPaints();
    }

    private void setWheelBounds(int width, int height) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int tempMinValue = Math.min(width - paddingLeft - paddingRight, height - paddingTop - paddingBottom);
        int circleDiameter = Math.min(tempMinValue, mWheelRadius * 2 - mBarWidth * 2);

        float xOffset = (width - paddingLeft - paddingRight - circleDiameter) / 2 - mBarWidth;
        float yOffset = (height - paddingTop - paddingBottom - circleDiameter) / 2 - mBarWidth;

        mWheelBounds = new RectF(xOffset, yOffset, xOffset + mWheelRadius, yOffset + mWheelRadius);
    }

    //需要重新测量View：方便配合自定义属性radius
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int viewWidth = getPaddingLeft() + mWheelRadius * 2 + getPaddingRight() + mBarWidth * 2;
        int viewHeight = getPaddingTop() + mWheelRadius * 2 + getPaddingBottom() + mBarWidth * 2;

        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width, height;
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = measureWidth;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(viewWidth, measureWidth);
                break;
            default:
                width = viewWidth;
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = measureHeight;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(viewHeight, measureHeight);
                break;
            default:
                height = viewHeight;
                break;
        }

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mWheelBounds, 0, 360, false, mRimPaint);

        boolean isCallInvalidate = false;

        //分具体两种情况画弧形：1)固定长度的弧形旋转；2)非旋转，每秒递增至目标进度；
        if (isSpinning) {
            isCallInvalidate = true;

            long timeOffset = SystemClock.uptimeMillis() - mLastAnimationTime;
            float anglesOffset = timeOffset * mSpinSpeed / 1000.0f;

            updateBarLength(timeOffset);
            mProgress += anglesOffset;

            //完成一圈转动之后
            if (mProgress > 360) {
                mProgress -= 360f;
            }
            mLastAnimationTime = SystemClock.uptimeMillis();
            float from = mProgress - 90;
            float length = INIT_BAR_LENGTH + barExtraLength;

            if (isInEditMode()) {
                from = 0;
                length = 135;
            }

            canvas.drawArc(mWheelBounds, from, length, false, mBarPaint);
        }

        if (isLoadingFinished) {
            //加载结束绘制正圆环
            canvas.drawArc(mWheelBounds, 0, 360, false, mBarPaint);
            isFirstLine = true;
            isSpinning = false;

            int centerX = (int) mWheelBounds.centerX();
            int centerY = (int) mWheelBounds.centerY();

            //开始绘制第一根线
            if (isFirstLine && !isSecondLine) {
                canvas.drawLine(centerX - mTickSize / 2, centerY, centerX - mTickSize / 2 + mTickSize / 4 * mLineSpeed, centerY + mTickSize / 2 * mLineSpeed, mBarPaint);

                if (mLineSpeed >= 1.0f) {
                    isSecondLine = true;
                    mLineSpeed = 0.0f;
                } else {
                    mLineSpeed += 0.1f;
                }
                invalidate();
            } else if (isFirstLine && isSecondLine) {
                canvas.drawLine(centerX - mTickSize / 2, centerY, centerX - mTickSize / 4, centerY + mTickSize / 2, mBarPaint);
                canvas.drawLine(centerX - mTickSize / 4, centerY + mTickSize / 2, centerX + mTickSize / 2 * mLineSpeed, centerY + mTickSize / 2 - mTickSize / 2 * mLineSpeed, mBarPaint);

                if (mLineSpeed < 1.0f) {
                    mLineSpeed += 0.1f;
                } else {
                    mLineSpeed = 0.0f;
                    isFirstLine = false;
                    isSecondLine = false;
                    isLoadingFinished = false;
                    return;
                }
                invalidate();
            }
        }

        if (isCallInvalidate) {
            invalidate();
        }
    }

    //l = n（圆心角）x π（圆周率）x r（半径）/180或者l =α(圆心角弧度数)× r（半径）
    /**
     * 更新进度条的长度
     *
     * @param timeOffset
     */
    private void updateBarLength(long timeOffset) {
        if (pausedTimeWithoutGrowing >= pauseGrowingTime) {
            timeStartGrowing += timeOffset;

            if (timeStartGrowing > barSpinCycleTime) {
                timeStartGrowing -= barSpinCycleTime;
                pausedTimeWithoutGrowing = 0;
                barGrowingFromFront = !barGrowingFromFront;
            }

            float distance = (float) Math.cos((timeStartGrowing / barSpinCycleTime + 1) * Math.PI) / 2 + 0.5F;
            float destLength = MAX_BAR_LENGTH - INIT_BAR_LENGTH;

            if (barGrowingFromFront) {
                barExtraLength = distance * destLength;
            } else {
                float newLength = destLength * (1 - distance);
                mProgress += (barExtraLength - newLength);
                barExtraLength = newLength;
            }

        } else {
            pausedTimeWithoutGrowing += timeOffset;
        }
    }

    public void setLoadingFinishedTag(boolean isFinished) {
        this.isLoadingFinished = isFinished;
    }

    public boolean getLoadingFinishedTag() {
        return isLoadingFinished;
    }
}
