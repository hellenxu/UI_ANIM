package com.six.ui.seekbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-12-11.
 */
class SixSeekbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val foregroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

    init {
        backgroundPaint.color = Color.LTGRAY
        backgroundPaint.style = Paint.Style.FILL

        foregroundPaint.color = Color.BLUE
        foregroundPaint.style = Paint.Style.FILL
    }

    //TODO
    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(110f, 200f, 600f, 200f + 80, 10f,10f, backgroundPaint)
        canvas.drawRoundRect(110f, 200f, 400f, 200f + 80, 10f,10f, foregroundPaint)
    }
}