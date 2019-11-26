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
    private var draggingFinished = true
    private var thumbX = 0f
    private var thumbY = 0f
    private var currentStopX = 0f

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
        if (draggingFinished) {
            drawStaticState(canvas)
        } else {
            drawDynamicState(canvas)
        }
    }

    // TODO
    private fun drawDynamicState(canvas: Canvas) {
        // draw the left half part: 3 points; color, strokeWidth
        val x1 = thumbX / 2
        val y1 = height / 2f
        val x2 = x1
        val y2 = thumbY
        path.cubicTo(x1, y1, x2, y2, thumbX, thumbY)
        paint.style = Paint.Style.STROKE
        paint.color = ContextCompat.getColor(ctx, DEFAULT_PRIMARY_COLOR_ID)
        paint.strokeWidth = PRIMARY_STROKE_WIDTH
        canvas.drawPath(path, paint)

        // draw the right half part
        path.reset()
        path.cubicTo(thumbX, thumbY, thumbX * 2 - x1, thumbY, thumbX * 2 - x1, y1)
        paint.color = ContextCompat.getColor(ctx, TRACK_COLOR_ID)
        paint.strokeWidth = SECONDARY_STROKE_WIDTH
        canvas.drawPath(path, paint)
    }

    private fun drawStaticState(canvas: Canvas) {
        paint.style = Paint.Style.STROKE
        // draw default track
        paint.strokeWidth = SECONDARY_STROKE_WIDTH
        paint.color = ContextCompat.getColor(ctx, TRACK_COLOR_ID)
        canvas.drawLine(startX, startY, stopX, stopY, paint)

        // draw primary track
        paint.strokeWidth = PRIMARY_STROKE_WIDTH
        paint.color = ContextCompat.getColor(ctx, DEFAULT_PRIMARY_COLOR_ID)
        currentStopX = stopX * currentPercentage
        canvas.drawLine(startX, startY, currentStopX, stopY, paint)

        // draw thumb
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = DEFAULT_THUMB_RADIUS
        canvas.translate(currentStopX + DEFAULT_THUMB_RADIUS, 0f)
        canvas.drawCircle(0f, stopY, DEFAULT_THUMB_RADIUS, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (hitThumb(event?.x ?: 0f, event?.y ?: 0f)) {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    draggingFinished = true
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    draggingFinished = false
                    thumbX = event.x
                    thumbY = event.y
                    invalidate()
                    return true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    draggingFinished = false
                    // start animation

                    return true

                }
            }
        }

        return super.onTouchEvent(event)
    }

    private fun hitThumb(x: Float, y: Float): Boolean {
        val leftBound = currentStopX
        val rightBound = currentStopX + DEFAULT_THUMB_RADIUS * 2
        val topBound = top + height / 2 - DEFAULT_THUMB_RADIUS
        val bottomBound = top + height / 2 + DEFAULT_THUMB_RADIUS
        return x > leftBound && x < rightBound && y > topBound && y < bottomBound
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