package com.six.ui.dragging;

import android.content.Context;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author hellenxu
 * @date 2016/1/18
 * Copyright 2016 Six. All rights reserved.
 */
public class DraggingView extends LinearLayout {
    private static final String TAG = DraggingView.class.getSimpleName();
    private final ViewDragHelper mDragHelper;
    private View mDragView;

    public DraggingView(Context context) {
        this(context, null);
    }

    public DraggingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this, 1.5f, new DragCallback());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //set the target view for dragging
        mDragView = getChildAt(0);
        mDragHelper.captureChildView(mDragView, mDragHelper.getActivePointerId());
    }

    /***
     * Call ViewDragHelper.processTouchEvent(MotionEvent event) to pass touching events to ViewDragHelper
     * returning true means that touching events are handled by DraggingView.
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        Log.d(TAG, "onTouchEvent: " + mDragHelper.checkTouchSlop(ViewDragHelper.DIRECTION_HORIZONTAL));
        return true;
    }

    /***
     * pass touching events to ViewDragHelper by calling ViewDragHelper.shouldInterceptTouchEvent(MotionEvent event) and cancel();
     * 1) MotionEvent.ACTION_CANCEL && MotionEvent.ACTION_DOWN: cancel();
     * 2) other kinds of events: shouldInterceptTouchEvent()
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_DOWN:
                mDragHelper.cancel();
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
//        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if(mDragHelper.continueSettling(true)){
            //postInvalidateOnAnimation(View view) causes an invalidate to happen on the next animation time step, typically the next display frame.
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /***
     * The most important part to handle touching events by ViewDragHelper:
     * calculating the target position by overriding clampViewPositionHorizontal or clampViewPositionVertical
     */
    private final class DragCallback extends ViewDragHelper.Callback {

        //true means the target is allowed to capture.
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.d(TAG, "pointerId: " + pointerId);
            return child.equals(mDragView);
//            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.d(TAG, "clampViewPositionHorizontal");
            final int leftPadding = getPaddingLeft();
            // restrict the target view within the cellphone screen horizontally
            final int maxLeft = getWidth() - leftPadding - child.getWidth();
            return Math.max(leftPadding, Math.min(maxLeft, left));
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.d(TAG, "clampViewPositionVertical");
            final int topPadding = getPaddingTop();
            //restrict the target view within the cellphone screen vertically
            final int maxTop = getHeight() - topPadding - child.getHeight();
            return Math.max(topPadding, Math.min(maxTop, top));
        }

        /***
         * three different states: 1) STATE_IDLE 0 ; 2) STATE_DRAGGING 1 ; 3) STATE_SETTLING 2
         * @param state
         */
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            Log.d(TAG, "onViewDragStateChanged-state: " + state);
        }

        //when tryCaptureView return true, onViewPositionChanged will be called.
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            Log.d(TAG, "onViewPositionChanged");
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            Log.d(TAG, "onViewCaptured");
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.d(TAG, "onViewReleased");
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            Log.d(TAG, "onEdgeTouched");
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            Log.d(TAG, "onEdgeLock");
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            Log.d(TAG, "onEdgeDragStarted");
        }

        /***
         * when an instance of ViewDragHelper handles onTouchEvent or onInterceptTouchEvent, getOrderedChildIndex will be called.
         */
        @Override
        public int getOrderedChildIndex(int index) {
            Log.d(TAG, "getOrderedChildIndex: " + index);
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            Log.d(TAG, "getViewHorizontalDragRange");
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            Log.d(TAG, "getViewVerticalDragRange");
            return super.getViewVerticalDragRange(child);
        }
    }
}