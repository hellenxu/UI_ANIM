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
        tmp.color = ContextCompat.getColor(ctx, DEFAULT_PRIMARY_COLOR_ID)
        tmp.style = Paint.Style.STROKE
        tmp.strokeWidth = PRIMARY_STROKE_WIDTH
        tmp
    }
    private val path = Path()
    private var startX = 0f
    private var startY = 0f
    private var stopX = 0f
    private var stopY = 0f
    private var currentPercentage = .6f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        startX = left.toFloat()
        startY = (bottom - top) * 0.5f
        stopX = right.toFloat()
        stopY = startY
        println("xxl-startX: $startX; startY: $startY; stopX: $stopX; stopY: $stopY")
    }

    override fun onDraw(canvas: Canvas) {
        // draw default track
        paint.strokeWidth = SECONDARY_STROKE_WIDTH
        paint.color = ContextCompat.getColor(ctx,TRACK_COLOR_ID)
        canvas.drawLine(startX, startY, stopX, stopY, paint)

        // draw primary track
        paint.strokeWidth = PRIMARY_STROKE_WIDTH
        paint.color = ContextCompat.getColor(ctx, DEFAULT_PRIMARY_COLOR_ID)
        val currentStopX = stopX * currentPercentage
        canvas.drawLine(startX, startY, currentStopX, stopY, paint)

        // draw thumb
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = DEFAULT_THUMB_RADIUS
        canvas.translate(currentStopX + DEFAULT_THUMB_RADIUS, 0f)
        canvas.drawCircle(0f, stopY, DEFAULT_THUMB_RADIUS, paint)

    }

    // TODO
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    fun setPercentage(percentage: Float) {
        currentPercentage = percentage.coerceAtLeast(0f).coerceAtMost(1f)
    }

    companion object {
        private const val TRACK_COLOR_ID = R.color.grey_font
        private const val DEFAULT_PRIMARY_COLOR_ID = R.color.blue_500
        private const val SECONDARY_STROKE_WIDTH = 8F
        private const val PRIMARY_STROKE_WIDTH = 12F
        private const val DEFAULT_THUMB_RADIUS = 12F
    }

}