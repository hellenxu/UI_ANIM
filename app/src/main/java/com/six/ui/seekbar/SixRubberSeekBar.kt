package com.six.ui.seekbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.six.ui.R

/**
 * @author hellenxu
 * @date 2019-11-25
 * Copyright 2019 Six. All rights reserved.
 */
class SixRubberSeekBar @JvmOverloads constructor(private val ctx: Context,
                                                 val attrs: AttributeSet? = null,
                                                 val defStyleAttr: Int = 0) : View(ctx, attrs, defStyleAttr) {

    private val paint: Paint by lazy {
        val tmp = Paint()
        tmp.isAntiAlias = true
        tmp.isDither = true
        tmp.color = ContextCompat.getColor(ctx, R.color.blue_500)
        tmp
    }
    private val path = Path()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}