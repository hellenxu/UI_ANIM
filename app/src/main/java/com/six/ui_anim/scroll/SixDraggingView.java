package com.six.ui_anim.scroll;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * 7 methods to implement moving
 * Created by xxl on 16/2/22.
 */

public class SixDraggingView extends View {
    private static final String TAG = SixDraggingView.class.getSimpleName();
    private int lastX;
    private int lastY;
    private Scroller mScroller;

    public SixDraggingView(Context context) {
        this(context, null);
    }

    public SixDraggingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SixDraggingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    /**
     * method 1: layout(int left, int top, int right, int bottom); left, top, right and bottom are positions relative to parent.
     * method 2: offsetLeftAndRight(int offsetX) && offsetTopAndBottom(int offsetY)
     * method 3: XX.LayoutParams(), it would be more convenient to use ViewGroup.MarginLayoutParams since you don't need to consider the type of layout of parent view.
     * method 4: scrollTo(int x, int y) or scrollBy(int offsetX, int offsetY). Remember to use parent view to call these two methods as it is the visible area moving rather
     * than the child view moving.
     * method 5: Scroller && overriding computeScroll()
     * 1) call Scroller.startScroll() to start scrolling;
     * 2) call invalidate() to make sure the scrolling effect is visible;
     * 3) override computeScroll(), calling invalidate() when scrolling is on.
     * method 6: object animation.
     * method 7: ViewDragHelper.
     * Sample Link: https://github.com/hellenxu/new_features/blob/master/app/src/main/java/com/six/newfeatures/DraggingView.java
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
//                lastX = (int) event.getRawX();
//                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = (int) (event.getX() - lastX);
                int offsetY = (int) (event.getY() - lastY);
// method 1: layout
//                int offsetX = (int) (event.getRawX() - lastX);
//                int offsetY = (int) (event.getRawY() - lastY);
//                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
//                lastX = (int) event.getRawX();
//                lastY = (int) event.getRawY();

// method 2: offsetLeftAndRight(int offsetX) && offsetTopAndBottom(int offsetY)
//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);

// method 3: setLayoutParams(LayoutParams params)
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + offsetX;
//                layoutParams.topMargin = getTop() + offsetY;
//                setLayoutParams(layoutParams);
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + offsetX;
//                layoutParams.topMargin = getTop() + offsetY;
//                setLayoutParams(layoutParams);

// method 4: scrollBy(int offsetX, int offsetY)
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                break;
            case MotionEvent.ACTION_UP:
                View viewGroup = (View) getParent();
                mScroller.startScroll(viewGroup.getScrollX(), viewGroup.getScrollY(), -viewGroup.getScrollX(), -viewGroup.getScrollY());
                invalidate();//call invalidate() here to make sure bouncing effect is visible since onDraw will be called after invalidate().
                break;
            default:
                Log.d(TAG, "default value");
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.d(TAG, "computeScroll");
        if (mScroller.computeScrollOffset()) {
            //Scroller.getCurrX && getCurrY is the current offset in scrolling.
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        Log.d(TAG, "invalidate");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");
    }
}
