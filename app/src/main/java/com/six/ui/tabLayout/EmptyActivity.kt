package com.six.ui.tabLayout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.six.ui.R

/**
 * @author hellenxu
 * @date 2020-06-21
 * Copyright 2020 Six. All rights reserved.
 */
class EmptyActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_empty)
    }

    fun toSample(view: View) {
        // exitResId =0 --> black background
        val transition = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.enter_from_bottom, R.anim.stay_still).toBundle()

        startActivity(Intent(this, CoordinatorLayoutTabLayoutVP2::class.java), transition)
    }
}