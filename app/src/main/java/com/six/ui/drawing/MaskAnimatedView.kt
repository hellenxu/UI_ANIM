package com.six.ui.drawing

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.six.ui.R
import kotlin.math.max

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
        context.getString(DEFAULT_TEXT_OTHER_RES_ID)
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

    private val textBounds = Rect()

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
        var textLength = textSpace
        tabTitles.forEach {
            textLength += textPaint.measureText(it) + textSpace
        }
        bgRight = max(bgRight, textLength - textSpace)
        fgRight = max(textPaint.measureText(tabTitles[0]) + textSpace, bgRight / 2)

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

//            // step 2: draw foreground
            it.drawRoundRect(fgLeft, fgTop, fgRight, fgBottom, roundX, roundY, fgPaint)

            // step 3: draw text
            currentTextStart = textSpace / 2
            tabTitles.forEach {title ->
                textPaint.getTextBounds(title, 0, title.length, textBounds)
                it.drawText(title, currentTextStart, textBaseline + textBounds.height() / 3, textPaint)
                currentTextStart += textBounds.width() + textSpace
            }
        }
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