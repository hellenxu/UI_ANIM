package com.six.ui.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * @author hellenxu
 * @date 2017-02-03
 * Copyright 2017 Six. All rights reserved.
 * LeftAlignSnapHelper is only for horizontal orientation.
 */

public class LeftAlignSnapHelper extends LinearSnapHelper {
    private OrientationHelper horizontalHelper;

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager){
        if(horizontalHelper == null){
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return horizontalHelper;
    }

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2]; // save distance offset horizontally and vertically.
        if(layoutManager.canScrollHorizontally()){
            OrientationHelper helper = getHorizontalHelper(layoutManager);
            // get the exact x coordinate, y can be ignored since in this case we consider horizontal orientation only
            out[0] = helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
        } else {
            out[0] = 0;
        }

        return out; //return the distance offset after final snapping
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper helper = getHorizontalHelper(layoutManager);
        if(layoutManager instanceof LinearLayoutManager){
            int firstVisiblePos = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            int lastVisiblePos = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            if(firstVisiblePos == RecyclerView.NO_POSITION){ // no item
                return null;
            }

            if(lastVisiblePos == layoutManager.getItemCount() - 1){
                return layoutManager.findViewByPosition(lastVisiblePos);
            }

            View firstVisibleChild = layoutManager.findViewByPosition(firstVisiblePos);
            // if more than half of the first visible child disappears from screen, then return the next item view;
            // otherwise, return first visible item.
            if(helper.getDecoratedEnd(firstVisibleChild) > 0
                    && helper.getDecoratedEnd(firstVisibleChild)
                    >= helper.getDecoratedMeasurement(firstVisibleChild) / 2){
                return firstVisibleChild;
            } else {
                return layoutManager.findViewByPosition(firstVisiblePos + 1);
            }

        }
        return super.findSnapView(layoutManager);
    }
}
