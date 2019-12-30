package com.six.ui.seekbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.dynamicanimation.animation.FloatValueHolder
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
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
        tmp.style = Paint.Style.STROKE
        tmp.color = ContextCompat.getColor(ctx, DEFAULT_TRACK_COLOR_ID)
        tmp
    }
    private val path = Path()
    private var springAnimation: SpringAnimation? = null
    private var trackY = 0f
    private var trackX = 0f
    private var thumbX = 0f
    private var thumbY = 0f
    private var currentPercentage = .6f
    private var thumbRadius = DEFAULT_THUMB_RADIUS
    private var draggingFinished = false

    init {
        getCustomAttributes()
    }

    // TODO
    private fun getCustomAttributes() {

    }

    // TODO make sure padding, margin and custom size are working well
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        trackX = width * currentPercentage  + left
        trackY = (bottom - top) * 0.5f
        thumbX = trackX
        thumbY = trackY
    }

    override fun onDraw(canvas: Canvas?) {
        if (!draggingFinished) {
            trackX = left + width * currentPercentage

            drawTrack(canvas)
            drawThumb(canvas)
        }
    }

    private fun drawThumb(canvas: Canvas?) {
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = ContextCompat.getColor(ctx, HIGHLIGHTED_TRACK_COLOR_ID)
        canvas?.drawCircle(thumbX, thumbY, thumbRadius, paint)
    }

    private fun drawTrack(canvas: Canvas?) {
        if (thumbY == trackY) {
            drawStaticState(canvas)
        } else {
            drawCubic(canvas)
        }
    }

    private fun drawStaticState(canvas: Canvas?) {
        // first part
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = DEFAULT_HIGHLIGHTED_STROKE_WIDTH
        paint.color = ContextCompat.getColor(ctx, HIGHLIGHTED_TRACK_COLOR_ID)
        val y = height * 0.5f
        canvas?.drawLine(left.toFloat(), trackY, thumbX, trackY, paint)
        // second part
        paint.strokeWidth = DEFAULT_NORMAL_STROKE_WIDTH
        paint.color = ContextCompat.getColor(ctx, DEFAULT_TRACK_COLOR_ID)
        canvas?.drawLine(thumbX, y, right.toFloat(), y, paint)
    }

    // correct coordinates
    private fun drawCubic(canvas: Canvas?) {
        // first part
        path.reset()
        paint.color = ContextCompat.getColor(ctx, HIGHLIGHTED_TRACK_COLOR_ID)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = DEFAULT_HIGHLIGHTED_STROKE_WIDTH
        val xOffSet = (trackX - left) / 2
        val x1 = left + xOffSet
        val y1 = (top + height / 2).toFloat()
        val x2 = (x1 + trackX) / 2
        val y2 = y1 + height / 2
        println("xxl-first: x1 = $x1; y1 = $y1; x2 = $x2; y2 = $y2; thumbX = $thumbX; thumbY = $thumbY")
        path.moveTo(left.toFloat(), trackY)
        path.cubicTo(x1, y1, x2, y2, thumbX, thumbY)
        canvas?.drawPath(path, paint)

        // second part
        path.reset()
        paint.color = ContextCompat.getColor(ctx, DEFAULT_TRACK_COLOR_ID)
        paint.strokeWidth = DEFAULT_NORMAL_STROKE_WIDTH
        println("xxl-second: x1 = $thumbX; y1 = $thumbY; x2 = ${thumbX + xOffSet}; y2 = $thumbY; thumbX = ${thumbX + xOffSet}; thumbY = $y1")
        path.moveTo(right.toFloat(), trackY)
        path.cubicTo(right - xOffSet, y1, thumbX + xOffSet, y2, thumbX, thumbY)
        canvas?.drawPath(path, paint)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && hitThumb(event.x, event.y)) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    draggingFinished = false
                    trackX = event.x
                    invalidate()
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    draggingFinished = false
                    springAnimation?.cancel()
                    thumbX = event.x
                    thumbY = event.y
                    invalidate()
                    return true
                }
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    draggingFinished = true
                    springAnimation = SpringAnimation(FloatValueHolder(trackY))
                        .setStartValue(thumbY)
                        .setSpring(
                            SpringForce(trackY)
                                .setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY)
                                .setStiffness(SpringForce.STIFFNESS_LOW)
                        )
                    springAnimation?.start()
                    return true
                }
            }
        }

        return super.onTouchEvent(event)
    }

    private fun hitThumb(x: Float, y: Float): Boolean {
        val leftBound = (thumbX - thumbRadius).coerceAtLeast(left.toFloat())
        val rightBound = (thumbX + thumbRadius).coerceAtMost(right.toFloat())
        val topBound = (top + height / 2 - thumbRadius).coerceAtLeast(top.toFloat())
        val bottomBound = (bottom - height / 2 + thumbRadius).coerceAtMost(bottom.toFloat())
        return x > leftBound && x < rightBound && y > topBound && y < bottomBound
    }

    fun setCurrentPercentage(percentage: Float) {
        currentPercentage = percentage
    }

    fun setThumbRadius(radius: Float) {
        thumbRadius = radius.coerceAtLeast(0f)
    }

    companion object {
        private const val DEFAULT_TRACK_COLOR_ID = R.color.grey_font
        private const val HIGHLIGHTED_TRACK_COLOR_ID = R.color.blue_200
        private const val DEFAULT_THUMB_RADIUS = 60f
        private const val DEFAULT_HIGHLIGHTED_STROKE_WIDTH = 15f
        private const val DEFAULT_NORMAL_STROKE_WIDTH = 10f
    }

}