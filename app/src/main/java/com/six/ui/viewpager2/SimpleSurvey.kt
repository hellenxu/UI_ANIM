package com.six.ui.viewpager2

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import com.six.ui.R


/**
 * @author hellenxu
 * @date 2020-05-22
 * Copyright 2020 Six. All rights reserved.
 */
class SimpleSurvey @JvmOverloads constructor(
        @NonNull ctx: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attrs, defStyleAttr) {

    init {
        val view = View.inflate(ctx, R.layout.simple_survey, this)

        view.findViewById<ImageView>(R.id.icon).setOnClickListener {
            val view = view.findViewById<TextView>(R.id.cta)
            if (view.visibility != View.VISIBLE) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }

            val rlayRoot = this.parent as ViewGroup
            println("szw root = $rlayRoot")
            println("szw root's focus  = ${rlayRoot.findFocus()}")
            val count = rlayRoot.childCount
            for (i in 0 until count) {
                val child = rlayRoot.getChildAt(i)
                println("szw focus lies in : (${child.hasFocus()}) - $child")
            }
        }
    }
}