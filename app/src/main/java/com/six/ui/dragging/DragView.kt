package com.six.ui.dragging

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

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
class DragView {
}