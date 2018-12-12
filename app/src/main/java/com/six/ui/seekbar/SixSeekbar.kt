package com.six.ui.seekbar

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-12-11.
 */
class SixSeekbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), Animatable {

    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val foregroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private val loadingAnimator: ValueAnimator
    private val backRectF: RectF

    private var currentUsage: Float = 0f
    private var currentRight: Float = 0f

    init {
        //paint init
        backgroundPaint.color = Color.LTGRAY
        backgroundPaint.style = Paint.Style.FILL

        foregroundPaint.color = Color.BLUE
        foregroundPaint.style = Paint.Style.FILL

        //animation init
        loadingAnimator = ValueAnimator.ofFloat(0f, 1f)
        loadingAnimator.interpolator = LinearInterpolator()
        loadingAnimator.addUpdateListener { animation ->
            computeProgress(animation.getAnimatedValue() as Float)
            invalidate()
        }
        loadingAnimator.duration = 1500

        currentUsage = 800f

        backRectF = RectF(110f, 200f, 1000f, 200f + 100)

    }

    private fun computeProgress(percentage: Float) {
        currentRight = percentage * currentUsage
        currentUsage = 800f
    }

    //TODO
    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(backRectF, 20f,20f, backgroundPaint)
        canvas.drawRoundRect(110f, 200f, currentRight, 200f + 100, 20f,20f, foregroundPaint)
    }

    override fun isRunning(): Boolean {
        return loadingAnimator.isRunning
    }

    override fun start() {
        loadingAnimator.start()
    }

    override fun stop() {
        loadingAnimator.cancel()
    }

    //TODO increase touching sensitivity
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if ((event.action == MotionEvent.ACTION_DOWN) and (backRectF.contains(event.x, event.y))) {
            val percentage: Float
            if(event.x < backRectF.width()) {
                percentage = event.x / backRectF.width()
            } else {
                percentage = 1f
                currentUsage = backRectF.right
            }
            computeProgress(percentage)
            invalidate()
            return true
        } else {
            return super.onTouchEvent(event)
        }
    }


}