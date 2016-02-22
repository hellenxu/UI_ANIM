package com.six.ui_anim.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 7 methods to implement moving
 * Created by xxl on 16/2/22.
 */

public class SixDraggingView extends View {
    private int lastX;
    private int lastY;

    public SixDraggingView(Context context) {
        this(context, null);
    }

    public SixDraggingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SixDraggingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //method 1: layout(int left, int top, int right, int bottom)
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                lastX = (int) event.getX();
//                lastY = (int) event.getY();
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
//                int offsetX = (int) (event.getX() - lastX);
//                int offsetY = (int) (event.getY() - lastY);
                int offsetX = (int) (event.getRawX() - lastX);
                int offsetY = (int) (event.getRawY() - lastY);
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);

                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
