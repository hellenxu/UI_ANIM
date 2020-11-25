package com.six.ui.xmode

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.six.ui.R

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
    private var tabTitles = listOf<String>(
        context.getString(DEFAULT_TEXT_FOCUSED_RES_ID),
        context.getString(DEFAULT_TEXT_OTHER_RES_ID)
    )
    private var roundX = DEFAULT_ROUND
    private var roundY = DEFAULT_ROUND
    private var bgLeft = 0f
    private var bgTop = 0f
    private var bgRight = 500f
    private var bgBottom = DEFAULT_HEIGHT
    private var fgLeft = 0f
    private var fgTop = 0f
    private var fgRight = 200f
    private var fgBottom = DEFAULT_HEIGHT


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

    // TODO
    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            // step 1: draw background
            it.drawRoundRect(bgLeft, bgTop, bgRight, bgBottom, roundX, roundY, bgPaint)

            // step 2: draw foreground
            it.drawRoundRect(fgLeft, fgTop, fgRight, fgBottom, roundX, roundY, fgPaint)
        }
    }

    companion object {
        private const val DEFAULT_TEXT_FOCUSED_RES_ID = R.string.text_focused
        private const val DEFAULT_TEXT_OTHER_RES_ID = R.string.text_other
        private const val DEFAULT_ROUND = 50f
        private const val DEFAULT_HEIGHT = 100f
    }

}