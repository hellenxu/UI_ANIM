package com.six.ui.seekbar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MotionEvent
import com.six.ui.R
import java.text.DecimalFormat

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-12-27.
 */


class SixSeekbar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {

        //default text settings
        const val DEFAULT_LIMIT = "8.00 GB"
        const val LIMIT_FORMAT = "%s GB"

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
    private val decFormat = DecimalFormat("#.00")


    private var frameRectF = RectF(50f, 310f, 1350f, 310f + 120)
    private var usedRatio = 0.2f
    private var limitRatio = 0.6f
    private var usedRight = 0f
    private var currentUsedRight = 0f
    private var limitRight = 0f
    private var currentLimitRight = 0f
    private var limitText = DEFAULT_LIMIT
    private var circleX = 0f
    private var circleY = 0f
    private var txtPosX = 0f
    private var txtPosY = 0f
    private var totalLimit = 8
    private var isFinished = true

    init {
        graphicInit(attrs)
        roundCorner = dp2px(15f)
    }


    private fun graphicInit(attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SixSeekbar)

        framePaint.color = ta.getColor(R.styleable.SixSeekbar_bgColor, ContextCompat.getColor(context, R.color.defBorderColor))
        framePaint.style = Paint.Style.STROKE
        framePaint.strokeWidth = dp2px(1f)

        limitPaint.color = ta.getColor(R.styleable.SixSeekbar_limitColor, ContextCompat.getColor(context, R.color.defLimitColor))
        limitPaint.style = Paint.Style.FILL

        usedPaint.color = ta.getColor(R.styleable.SixSeekbar_usedColor, ContextCompat.getColor(context, R.color.defUsedColor))
        usedPaint.style = Paint.Style.FILL

        textPaint.color = ContextCompat.getColor(context, R.color.defTextColor)
        textPaint.textSize = ta.getDimension(R.styleable.SixSeekbar_textSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, metrics))

        ta.recycle()

        textPaint.getTextBounds(limitText, 0, limitText.length, limitBounds)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(frameRectF, roundCorner, roundCorner, framePaint)
        canvas.drawRoundRect(frameRectF.left, frameRectF.top, currentLimitRight, frameRectF.bottom, roundCorner, roundCorner, limitPaint)
        if(currentUsedRight <= currentLimitRight) {
            canvas.drawRoundRect(frameRectF.left, frameRectF.top, currentUsedRight, frameRectF.bottom, roundCorner, roundCorner, usedPaint)
        }

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

    private val touchRectF = RectF()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val drawingRect = Rect()
        getDrawingRect(drawingRect)

        val frameLeft = drawingRect.left + dp2px(HORIZONTAL_OFFSET)
        val frameTop = (drawingRect.top + dp2px(VERTICAL_OFFSET))
        val frameRight = drawingRect.right - dp2px(HORIZONTAL_OFFSET) * 2 - limitBounds.width()
        val frameBottom = (drawingRect.bottom - dp2px(VERTICAL_OFFSET))

        frameRectF.set(frameLeft, frameTop, frameRight, frameBottom)

        limitRight = (limitRatio * frameRectF.width()) + frameLeft
        usedRight = (usedRatio * limitRatio * frameRectF.width()) + frameLeft
        currentLimitRight = limitRight
        currentUsedRight = usedRight

        circleX = limitRight
        circleY = frameRectF.top + frameRectF.height() / 2

        txtPosX = frameRectF.right + dp2px(HORIZONTAL_OFFSET)
        txtPosY = frameRectF.top + frameRectF.height() / 2 + limitBounds.height() / 2

        val touchLeft = frameLeft
        val touchTop = frameTop - (dp2px(INDICATOR_RADIUS) - frameRectF.height() / 2)
        val touchRight = frameRight
        val touchBottom = frameBottom + (dp2px(INDICATOR_RADIUS) - frameRectF.height() / 2)
        touchRectF.set(touchLeft, touchTop, touchRight, touchBottom)
    }

    private fun dp2px(dp: Float): Float {
        return metrics.density * dp
    }

    private fun drawText(canvas: Canvas) {
        val currentRatio = decFormat.format(currentLimitRight / (frameRectF.right - dp2px(INDICATOR_RADIUS)) * totalLimit)
        limitText = String.format(LIMIT_FORMAT, currentRatio)
        canvas.drawText(limitText, txtPosX, txtPosY, textPaint)
    }

    private fun drawIndicator(canvas: Canvas) {
        canvas.drawCircle(circleX, circleY, dp2px(INDICATOR_RADIUS), framePaint)
        canvas.drawCircle(circleX, circleY, dp2px(INDICATOR_RADIUS), limitPaint)
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

    fun setTotalLimit(total: Int) {
        totalLimit = total
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(!isFinished or touchRectF.contains(event.x, event.y)) {
            parent.requestDisallowInterceptTouchEvent(true) //not allow parent to take care of touch event
            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    isFinished = false
                }

                MotionEvent.ACTION_MOVE -> {
                    val targetX = Math.max(Math.min(event.x, frameRectF.right - dp2px(INDICATOR_RADIUS)), frameRectF.left + dp2px(INDICATOR_RADIUS))
                    circleX = targetX
                    currentLimitRight = targetX
                    invalidate()
                }

                MotionEvent.ACTION_UP -> {
                    isFinished = true
                }
            }

            return true
        }
        return super.onTouchEvent(event)
    }

}