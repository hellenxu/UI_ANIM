package com.six.ui_anim.anim_3x;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.six.ui_anim.R;

/**
 * @author hellenxu
 * @date 2015/10/15
 * Copyright 2015 Six. All rights reserved.
 */

public class ValueAnimatorActivity extends Activity {
    private static final String TAG = ValueAnimatorActivity.class.getSimpleName();
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_point_anim);
//        setContentView(R.layout.act_anim);

//        tvTitle = (TextView) findViewById(R.id.tv_title);
//        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f, 5f, 3f, 10f);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(tvTitle, "alpha", 0f, 0.5f, 1f);
//        animator.setDuration(3000);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float currentValue = (float) animation.getAnimatedValue();
//                Log.d(TAG, "current value is " + currentValue);
//            }
//        });
//        animator.start();

//        Animator animatorXml = AnimatorInflater.loadAnimator(this, R.anim.anim_value);
//        animatorXml.setDuration(4000);
//        animatorXml.setTarget(tvTitle);
//        animatorXml.start();

    }
}
