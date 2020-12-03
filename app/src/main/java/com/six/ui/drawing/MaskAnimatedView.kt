package com.six.ui.drawing

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.six.ui.R
import kotlin.math.max
import kotlin.math.min

/**
 * @author hellenxu
 * @date 2020-11-25
 * Copyright 2020 Six. All rights reserved.
 */

//TODO two phrases
/**
 * 1, fixed tab count: 2 tabs
 * 2, flexible tab count
 */
class MaskAnimatedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var tabBackgroundColor = ContextCompat.getColor(context, R.color.colorPrimary)
    private var tabForegroundColor = ContextCompat.getColor(context, R.color.semi_white)
    private var textColor = Color.parseColor("#ffffff")
    private var tabTitles = listOf<String>(
        context.getString(DEFAULT_TEXT_FOCUSED_RES_ID),
        context.getString(DEFAULT_TEXT_OTHER_RES_ID),
        context.getString(R.string.home)
    )
    private var roundX = DEFAULT_ROUND
    private var roundY = DEFAULT_ROUND
    private var bgLeft = 0f
    private var bgTop = 0f
    private var bgRight = 0f
    private var bgBottom = 0f
    private var fgLeft = 0f
    private var fgTop = 0f
    private var fgRight = bgRight / 2
    private var fgBottom = bgBottom
    private var currentTextStart = 0f
    private var textBaseline = 0f
    private var textSpace = 0f
    private var maxTextWidth = 0f

    private val textBounds = Rect()
    private val touchBounds = RectF()
    private var downX = 0f

    private val metrics: DisplayMetrics by lazy {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        DisplayMetrics().apply {
            wm.defaultDisplay.getMetrics(this)
        }
    }

    private val bgPaint: Paint by lazy {
        Paint().apply {
            color = tabBackgroundColor
            flags = Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG
            style = Paint.Style.FILL
        }
    }
    private val fgPaint: Paint by lazy {
        Paint().apply {
            color = tabForegroundColor
            flags = Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG
            style = Paint.Style.FILL
        }
    }
    private val textPaint: TextPaint by lazy {
        TextPaint().apply {
            color = textColor
            flags = Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG
            style = Paint.Style.STROKE
            textSize = sp2px(DEFAULT_TEXT_SIZE)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        textSpace = dp2px(DEFAULT_TEXT_SPACE)
        textBaseline = measuredHeight / 2f
        bgBottom = dp2px(DEFAULT_HEIGHT)
        fgBottom = bgBottom
        roundX = dp2px(DEFAULT_ROUND)
        roundY = roundX

        // adjust width
        maxTextWidth = textSpace
        tabTitles.forEach {
            maxTextWidth = max(textPaint.measureText(it) + textSpace, maxTextWidth)
        }
        bgRight = max(bgRight, maxTextWidth * tabTitles.size)
        fgRight = bgRight / tabTitles.size

        touchBounds.set(fgLeft, fgTop, fgRight, fgBottom)

        println("xxl-onSizeChanged: $bgRight; $fgRight")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val height = when (heightMode) {
            MeasureSpec.AT_MOST -> {
                dp2px(DEFAULT_HEIGHT).toInt()
            }

            else -> {
                heightSize
            }
        }

        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, heightMode))
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            // step 1: draw background
            it.drawRoundRect(bgLeft, bgTop, bgRight, bgBottom, roundX, roundY, bgPaint)

            // step 2: draw foreground
            fgLeft = fgLeft.coerceAtLeast(bgLeft).coerceAtMost(bgRight - maxTextWidth)
            fgRight = fgRight.coerceAtLeast(bgLeft + maxTextWidth).coerceAtMost(bgRight)
            it.drawRoundRect(fgLeft, fgTop, fgRight, fgBottom, roundX, roundY, fgPaint)
            touchBounds.set(fgLeft, fgTop, fgRight, fgBottom)

            // step 3: draw text
            currentTextStart = 0f
            tabTitles.forEach {title ->
                if (currentTextStart <= fgLeft) {
                    textPaint.color = tabBackgroundColor
                } else {
                    textPaint.color = tabForegroundColor
                }
                textPaint.getTextBounds(title, 0, title.length, textBounds)
                currentTextStart += (maxTextWidth - textBounds.width()) / 2
                it.drawText(title, currentTextStart, textBaseline + textBounds.height() / 3, textPaint)

                if (fgLeft > currentTextStart) {
                    canvas.save()
                    canvas.clipRect(touchBounds)
                    textPaint.color = tabBackgroundColor
                    canvas.drawText(title, currentTextStart, textBaseline + textBounds.height() / 3, textPaint)
                    canvas.restore()
                }

                currentTextStart += textBounds.width() + textSpace
            }
        }
    }

    // TODO
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()

        if (event != null && touchBounds.contains(event.x, event.y)) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.x
                }
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                    val offset = event.x - downX
                    fgLeft += offset
                    fgRight += offset

                    invalidate()
                }
            }
            return true
        }

        return super.onTouchEvent(event)
    }

    // TODO
    override fun performClick(): Boolean {
        return super.performClick()
    }

    // region helper functions
    private fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics)
    }

    private fun sp2px(sp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics)
    }
    // endregion

    companion object {
        private const val DEFAULT_TEXT_FOCUSED_RES_ID = R.string.text_focused
        private const val DEFAULT_TEXT_OTHER_RES_ID = R.string.text_other
        private const val DEFAULT_ROUND = 50f //dp
        private const val DEFAULT_HEIGHT = 50f //dp
        private const val DEFAULT_WIDTH = 100f // dp
        private const val DEFAULT_TEXT_SIZE = 16f //sp
        private const val DEFAULT_TEXT_SPACE = 8f //dp
    }

}