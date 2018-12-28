package com.six.ui.seekbar

import android.app.Activity
import android.os.Bundle
import com.six.ui.R
import kotlinx.android.synthetic.main.act_seekbar.*

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-12-11.
 */
class SeekbarSample: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_seekbar)
        slider2.setUsedRatio(.6f)
    }

//    override fun onResume() {
//        super.onResume()
//        usage.start()
////        bar2.start()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        usage.stop()
////        bar2.start()
//    }
}