package com.six.ui.bounce;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * @author hellenxu
 * @date 2016/3/7
 * Copyright 2016 Six. All rights reserved.
 */
public class SixBounceView extends ScrollView {
    private View mInner;
    private Rect mBouncer;
    private int lastY;
    private boolean isFirstTouch = false;
    private static final int DEFAULT_ANIM_DURATION = 200;

    public SixBounceView(Context context) {
        this(context, null);
    }

    public SixBounceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SixBounceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mInner = getChildAt(0);
        mBouncer = new Rect();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetY = (int) (ev.getY() - lastY);

                //NO.4
//                if (!isFirstTouch) {
//                    offsetY = 0;
//                }
                if (isMove()) { //NO.5
                    if (mBouncer.isEmpty()) {
                        mBouncer.set(mInner.getLeft(), mInner.getTop(), mInner.getRight(), mInner.getBottom());
                    }

                    mInner.layout(mInner.getLeft(), mInner.getTop() + offsetY / 2, mInner.getRight(), mInner.getBottom() + offsetY / 2);
                }
//                isFirstTouch = true;
                lastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (isStartBouncing()) {
                    startBouncing();
//                    isFirstTouch = false;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private boolean isStartBouncing() {
        return !mBouncer.isEmpty();
    }

    /**
     * two situations to check whether scrolling is finished:
     * 1) scrollY == 0: no scrolling; 2) scrollY == offsetY: scrolling is going.
     */
    private boolean isMove() {
        int scrollY = getScrollY();
//        int offsetY = mInner.getMeasuredHeight() - mInner.getHeight(); NO.1 getMeasuredHeight() VS getHeight()
        int offsetY = mInner.getMeasuredHeight() - getHeight();
        return scrollY == offsetY || scrollY == 0;
    }

    private void startBouncing() {
        TranslateAnimation transAnim = new TranslateAnimation(0, 0, mInner.getTop(), mBouncer.top);
        transAnim.setDuration(DEFAULT_ANIM_DURATION);
//        transAnim.start(); NO.2
        mInner.startAnimation(transAnim);
        mInner.layout(mBouncer.left, mBouncer.top, mBouncer.right, mBouncer.bottom); //NO.3 reset the position of inner view
        mBouncer.setEmpty();
    }
}
