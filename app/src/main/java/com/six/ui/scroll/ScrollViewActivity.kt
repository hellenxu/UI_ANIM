package com.six.ui.scroll

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.six.ui.R
import kotlinx.android.synthetic.main.act_sv.*

/**
 * @author hellenxu
 * @date 2019-01-16
 * Copyright 2019 Six. All rights reserved.
 */
class ScrollViewActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_sv)

        val loadingView = View.inflate(this, R.layout.view_loading, null)
        val lp = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        loadingView.layoutParams = lp
        container.addView(loadingView)
    }
}