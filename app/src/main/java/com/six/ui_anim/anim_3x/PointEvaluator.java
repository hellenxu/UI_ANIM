package com.six.ui_anim.anim_3x;

import android.animation.TypeEvaluator;

/**
 * @author hellenxu
 * @date 2015/10/15
 * Copyright 2015 Six. All rights reserved.
 */
public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
        Point currentPoint = new Point(x, y);
        return currentPoint;
    }
}
