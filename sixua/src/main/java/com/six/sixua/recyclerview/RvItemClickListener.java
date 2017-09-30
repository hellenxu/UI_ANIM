package com.six.sixua.recyclerview;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author hellenxu
 * @date 2017-02-02
 * Copyright 2017 Six. All rights reserved.
 */

public abstract class RvItemClickListener implements RecyclerView.OnItemTouchListener {
    private RecyclerView rv;
    private GestureDetectorCompat gestureDetector;

    private class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if(child != null){
                onItemClick(rv.getChildViewHolder(child));
            }
            return true; // return false means does nothing.
        }
    }

    public RvItemClickListener(RecyclerView rv){
        this.rv = rv;
        gestureDetector = new GestureDetectorCompat(rv.getContext(), new GestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public abstract void onItemClick(RecyclerView.ViewHolder vh);
}
