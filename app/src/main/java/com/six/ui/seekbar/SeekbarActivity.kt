package com.six.ui.seekbar

import android.app.Activity
import android.os.Bundle
import com.six.ui.R
import kotlinx.android.synthetic.main.frag_travel.*

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-12-29.
 */
class SeekbarActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frag_travel)

        slider2.setLimitRatio(.7f)
        slider2.setUsedRatio(.05f)
        slider2.setTotalLimit(12)
    }


}