package com.six.ui.seekbar

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator

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
    private val metrics = resources.displayMetrics
    private val MIN_HEIGHT = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, metrics)

    private var currentUsage: Float = 0f
    private var currentRight: Float = 0f
    private var roundCornerRadius = 0f

    init {
        //paint init
        backgroundPaint.color = Color.parseColor("#FFF0F5")
        backgroundPaint.style = Paint.Style.FILL

        foregroundPaint.color = Color.parseColor("#FFB6C1")
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

        roundCornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, metrics)
    }

    private fun computeProgress(percentage: Float) {
        currentRight = percentage * currentUsage
        currentUsage = 800f
    }

    //TODO
    override fun onDraw(canvas: Canvas) {
//        canvas.save()
        canvas.drawRoundRect(backRectF, roundCornerRadius,roundCornerRadius, backgroundPaint)
        canvas.clipRect(currentRight - roundCornerRadius, backRectF.top, currentRight, backRectF.bottom, Region.Op.DIFFERENCE)
        canvas.drawRoundRect(backRectF.left, backRectF.top, currentRight, backRectF.bottom, roundCornerRadius,roundCornerRadius, foregroundPaint)
//        canvas.restore()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        backRectF.set((left + 30).toFloat(), (top + 30).toFloat(), (right - 30).toFloat() , (bottom - 30).toFloat())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var realHeight = 0

        when(heightMode){
            MeasureSpec.AT_MOST -> {
                realHeight = MIN_HEIGHT.toInt()
            }

            MeasureSpec.EXACTLY,
            MeasureSpec.UNSPECIFIED -> {
                realHeight = Math.max(MIN_HEIGHT.toInt(), height)
            }

        }

        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(realHeight, heightMode))
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

    //TODO increase touching sensitivity; ACTION_MOVE
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