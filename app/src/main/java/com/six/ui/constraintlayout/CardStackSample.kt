package com.six.ui.constraintlayout

import android.app.Activity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import com.six.ui.R

/**
 * @author hellenxu
 * @date 2019-10-23
 * Copyright 2019 Six. All rights reserved.
 */
class CardStackSample: Activity() {
    private lateinit var motionLayout: MotionLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_card_stack)

        motionLayout = findViewById(R.id.card_container_mlay)
        motionLayout.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                println("xxl-outside-currentId: ${getState(currentId)}")
                when (currentId) {
                    R.id.disappear_left, R.id.disappear_right -> {
                        println("xxl-currentId: left = ${currentId == R.id.disappear_left}; right = ${currentId == R.id.disappear_right}")
                        motionLayout?.progress = 0f
                        motionLayout?.setTransition(R.id.start, R.id.pass_end)
                    }
                }
            }
        })
    }

    private fun getState(id: Int): String {

        return when (id) {
            R.id.start -> "start"
            R.id.pass_end -> "pass_end"
            R.id.like_end -> "like_end"
            R.id.disappear_left -> "disappear_left"
            R.id.disappear_right -> "disappear_right"
            else -> "unknown"
        }
    }
}