package com.six.ui.sequencelayout

import android.app.Activity
import android.os.Bundle
import com.six.ui.R

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-07-04.
 */
class SampleActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sequence_item)
    }
}