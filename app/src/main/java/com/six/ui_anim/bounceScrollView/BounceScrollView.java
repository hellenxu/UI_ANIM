package com.six.ui_anim.bounceScrollView;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * @author hellenxu
 * @date 2015/12/9
 * Copyright 2015 Six. All rights reserved.
 */
public class BounceScrollView extends ScrollView {
    private View inner;
    private float y; //记录y轴坐标
    //用于判断是否需要动画?
    private Rect normal = new Rect();
    private boolean isCount = false;
    private final static String TAG = BounceScrollView.class.getSimpleName();

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BounceScrollView(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() > 0){
            inner = getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(inner != null){
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    //actually handling motion events
    private void commOnTouchEvent(MotionEvent ev){
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                //实现bounce的效果
                if(isNeedAnimation()){
                    animation();
                    isCount = false;//第一次touch的标志位
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final float preY = y;
                float nowY = ev.getY();
                int deltaY = (int) (preY - nowY);

                if(!isCount) {
                    deltaY = 0; //重置第一次touch的位移，若不重置第一次touch之后，normal反弹之后的坐标会超出屏幕之外
                }

                y = nowY;
                if(isNeedMove()){
                    if(normal.isEmpty()){
                        normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
                    }

                    //取deltaY的1/2的原因：就是反弹效果更好些，类似也可以取deltaY的1/3
                    //top和bottom的变化值不一致时，向上滑动没有反弹效果
                    inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2, inner.getRight(), inner.getBottom() - deltaY / 2);
                }
                isCount = true;
                break;
            default:
                break;
        }
    }

    /**
     * isEmpty(): a final method of Rect, returns true if the rectangle is empty(left >=right or top >= bottom)
     */
    private boolean isNeedAnimation(){
        return !normal.isEmpty();
    }

    /**
     * bounce动画的实现：实际就是平移动画，并在完成动画后调用setEmpty实现bounce的效果
     */
    private void animation(){
        TranslateAnimation transAnim = new TranslateAnimation(0, 0, inner.getTop(), normal.top);
        transAnim.setDuration(200);
        inner.startAnimation(transAnim);
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty(); // set the rectangle to (0, 0, 0, 0) 通过设置normal的上下左右为0实现bounce的效果
    }

    private boolean isNeedMove(){
        int offset = inner.getMeasuredHeight() - getHeight();//
        int scrollY = getScrollY();
        //当scrollY == 0: scrollView拖到顶部；
        return scrollY == 0 || scrollY == offset;
    }
}
