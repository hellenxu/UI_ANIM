package com.six.ui.seekbar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.util.TypedValue
import com.six.ui.R

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-12-27.
 */


class SixSeekbar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){

    companion object {

        //default text settings
        const val DEFAULT_LIMIT = "8 GB"

        //default dimensions in dp
        const val HORIZONTAL_OFFSET = 10f
        const val VERTICAL_OFFSET = 10f
        const val DEFAULT_HEIGHT = 30f
        const val INDICATOR_RADIUS = 12F

    }

    private val framePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private val limitPaint: Paint = Paint(framePaint)
    private val usedPaint: Paint = Paint(framePaint)
    private val textPaint: Paint = Paint(framePaint)
    private val metrics: DisplayMetrics = resources.displayMetrics
    private val limitBounds = Rect()
    private val roundCorner: Float


    private var frameRectF = RectF(50f, 310f, 1350f, 310f + 120)
    private var usedRatio = 0.2f
    private var limitRatio = 0.6f
    private var usedRight = 0f
    private var limitRight = 0f
    private var limitText = DEFAULT_LIMIT

    init {
        graphicInit(attrs)
        roundCorner = dp2px(15f)
    }


    private fun graphicInit(attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SixSeeksbar)

        framePaint.color = ta.getColor(R.styleable.SixSeeksbar_bgColor, ContextCompat.getColor(context, R.color.defBorderColor))
        framePaint.style = Paint.Style.STROKE
        framePaint.strokeWidth = dp2px(1f)

        limitPaint.color = ta.getColor(R.styleable.SixSeeksbar_limitColor, ContextCompat.getColor(context, R.color.defLimitColor))
        limitPaint.style = Paint.Style.FILL

        usedPaint.color = ta.getColor(R.styleable.SixSeeksbar_usedColor, ContextCompat.getColor(context, R.color.defUsedColor))
        usedPaint.style = Paint.Style.FILL

        textPaint.color = ContextCompat.getColor(context,R.color.defTextColor)
        textPaint.textSize = ta.getDimension(R.styleable.SixSeeksbar_textSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, metrics))

        ta.recycle()

        textPaint.getTextBounds(limitText, 0, limitText.length, limitBounds)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(frameRectF, roundCorner, roundCorner, framePaint)

        val offset = framePaint.strokeWidth
//        canvas.drawRoundRect(frameRectF.left + offset , frameRectF.top + offset, limitRight, frameRectF.bottom - offset, roundCorner, roundCorner, limitPaint)
//        canvas.drawRoundRect(frameRectF.left + offset, frameRectF.top + offset, usedRight, frameRectF.bottom - offset, roundCorner, roundCorner, usedPaint)
        canvas.drawRoundRect(frameRectF.left  , frameRectF.top, limitRight, frameRectF.bottom, roundCorner, roundCorner, limitPaint)
        canvas.drawRoundRect(frameRectF.left, frameRectF.top, usedRight, frameRectF.bottom, roundCorner, roundCorner, usedPaint)

        drawText(canvas)
        drawIndicator(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var realHeight = 0
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        when (heightMode) {
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                realHeight = dp2px(DEFAULT_HEIGHT).toInt()
            }
            MeasureSpec.EXACTLY -> {
                realHeight = Math.max(height, dp2px(DEFAULT_HEIGHT).toInt())
            }
        }

        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(realHeight, heightMode))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val drawingRect = Rect()
        getDrawingRect(drawingRect)

        val frameLeft = drawingRect.left + dp2px(HORIZONTAL_OFFSET)
        val frameTop = (drawingRect.top + dp2px(VERTICAL_OFFSET))
        val frameRight = drawingRect.right - dp2px(HORIZONTAL_OFFSET) * 2 - limitBounds.width()
        val frameBottom = (drawingRect.bottom - dp2px(VERTICAL_OFFSET))

        frameRectF.set(frameLeft, frameTop, frameRight, frameBottom)

        limitRight = (limitRatio * frameRectF.right)
        usedRight = (usedRatio * limitRight)
    }

    private fun dp2px(dp: Float): Float {
        return metrics.density * dp
    }

    private fun drawText(canvas: Canvas) {
        val x = frameRectF.right + dp2px(HORIZONTAL_OFFSET)
        val y = frameRectF.top + frameRectF.height() / 2 + limitBounds.height() / 2
        canvas.drawText(limitText, x, y, textPaint)
    }

    private fun drawIndicator(canvas: Canvas) {
        val cx = limitRight
        val cy = frameRectF.top + frameRectF.height() / 2
        canvas.drawCircle(cx, cy, dp2px(INDICATOR_RADIUS), framePaint)
        canvas.drawCircle(cx, cy, dp2px(INDICATOR_RADIUS), limitPaint)
    }

    fun setLimitColor(color: Int) {
        limitPaint.color = color
    }

    fun setUsedColor(color: Int) {
        usedPaint.color = color
    }

    fun setLimitRatio(ratio: Float) {
        limitRatio = ratio
    }

    fun setUsedRatio(ratio: Float) {
        usedRatio = ratio
    }

}