package com.six.ui.dragging

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.customview.widget.ViewDragHelper

/**
 * This DragView has normal and minimum states:
 * when it's dragged down with certain distance, then minimization animation is triggered;
 * after that, drag view in minimum state will pinned at the bottom of screen;
 * when minimum state view is dragged up with certain distance, then maximization animation is triggered;
 * the view will transform into normal state and restore to it's original position.
 *
 * @author hellenxu
 * @date 2020-10-15
 * Copyright 2020 Six. All rights reserved.
 */
class DragView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ViewGroup(ctx, attrs, defStyleAttr) {

    private var currentState: DragViewState = DragViewState.Normal

    // TODO
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

        when(currentState) {
            DragViewState.Normal -> {}

            DragViewState.MIN -> {}
        }
    }

    companion object {
        private const val MIN_HEIGHT_DP_DEFAULT = 80
    }

}

sealed class DragViewState {
    object Normal: DragViewState()
    object MIN: DragViewState()
}