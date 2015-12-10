package com.six.ui_anim.anim_3x;

import android.animation.TimeInterpolator;
import android.util.Log;

/**
 * @author hellenxu
 * @date 2015/10/16
 * Copyright 2015 Six. All rights reserved.
 */

public class DecelerateAccelerateInterpolator implements TimeInterpolator{
    private static final String TAG = DecelerateAccelerateInterpolator.class.getCanonicalName();

    @Override
    public float getInterpolation(float input) {
        float result;
        if(input <= 0.5){
            result = (float) Math.sin(Math.PI * input) / 2;
        } else {
            result = (float) (2 - Math.sin(Math.PI * input)) / 2;
        }
        Log.d(TAG, "result: " + result);
        return result;
    }
}
