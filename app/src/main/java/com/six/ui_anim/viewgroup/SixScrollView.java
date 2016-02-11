package com.six.ui_anim.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by xxl on 16/2/11.
 */

public class SixScrollView extends RelativeLayout {
    private View mDragTarget;

    public SixScrollView(Context context) {
        this(context, null);
    }

    public SixScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SixScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragTarget = getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int lastX = 0, lastY = 0;
        int offsetX = 0, offsetY = 0;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                offsetX = (int) (event.getX() - lastX);
                offsetY = (int) (event.getY() - lastY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onTouchEvent(event);
    }
}
