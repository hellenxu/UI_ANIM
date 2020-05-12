package com.six.ui.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.six.ui.R;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author hellenxu
 * @date 2015/7/10
 * Copyright 2015 Six. All rights reserved.
 */

public class GridDividerDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {android.R.attr.listDivider};
    private Drawable mDivider;
    private int mInsets;

    public GridDividerDecoration(Context ctx) {
        TypedArray ta = ctx.obtainStyledAttributes(ATTRS);
        mDivider = ta.getDrawable(0);
        assert mDivider != null;
        ta.recycle();
        mInsets = (int) ctx.getResources().getDimension(R.dimen.item_insets);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
//        drawHorizontal(c, parent);
//        drawVertical(c, parent);
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        if (parent.getChildCount() == 0) return;

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

//        solution one: calculate row count
        int rowCount = parent.getChildCount() / ((GridLayoutManager)parent.getLayoutManager()).getSpanCount();
        for (int i = 0; i < rowCount; i ++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }

// solution two: compare current bottom with parent bottom
//        final View child = parent.getChildAt(0);
//
//        if (child.getHeight() == 0) return;
//
//        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//        int top = child.getBottom() + params.bottomMargin;
//        int bottom = top + mDivider.getIntrinsicHeight();
//
//        final int parentBottom = parent.getHeight() - parent.getPaddingBottom();
//        while (bottom < parentBottom) {
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//            top += params.topMargin + child.getHeight() + params.bottomMargin;
//            bottom = top + mDivider.getIntrinsicHeight();
//        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int count = ((GridLayoutManager)parent.getLayoutManager()).getSpanCount() - 1;

        for (int i = 0; i < count; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, mInsets, mInsets);
//        outRect.set(0,0,mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
    }
}
