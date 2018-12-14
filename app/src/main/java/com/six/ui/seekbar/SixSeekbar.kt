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
    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private val loadingAnimator: ValueAnimator
    private val backRectF: RectF
    private val metrics = resources.displayMetrics
    private val MIN_HEIGHT = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, metrics)
    private val textBounds = Rect()
    private val text2Bounds = Rect()
    private val TEXT = "Used 75%"
    private val TEXT2 = "HUHU"

    private var currentUsage: Float = 0f
    private var currentRight: Float = 0f
    private var roundCornerRadius = 0f
    private var isLoadingFinished = false


    init {
        //paint init
        backgroundPaint.color = Color.parseColor("#FFF0F5")
        backgroundPaint.style = Paint.Style.FILL

        foregroundPaint.color = Color.parseColor("#FFB6C1")
        foregroundPaint.style = Paint.Style.FILL

        textPaint.color = Color.BLACK
        textPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, metrics)

        //animation init
        loadingAnimator = ValueAnimator.ofFloat(0f, 1f)
        loadingAnimator.interpolator = LinearInterpolator()
        loadingAnimator.addUpdateListener { animation ->
            isLoadingFinished = animation.animatedFraction == 1f
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
    }

    //TODO
    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(backRectF, roundCornerRadius,roundCornerRadius, backgroundPaint)
        canvas.clipRect(currentRight, backRectF.top, currentRight + roundCornerRadius, backRectF.bottom, Region.Op.DIFFERENCE)
        canvas.drawRoundRect(backRectF.left, backRectF.top, currentRight + roundCornerRadius, backRectF.bottom, roundCornerRadius,roundCornerRadius, foregroundPaint)

        if(isLoadingFinished) {
            canvas.drawText(TEXT, currentRight - textBounds.width() / 2 , backRectF.top * 0.8f, textPaint)
            canvas.drawText(TEXT2, backRectF.left , backRectF.bottom + text2Bounds.height() * 1.2f, textPaint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        textPaint.getTextBounds(TEXT, 0, TEXT.length, textBounds)
        textPaint.getTextBounds(TEXT2, 0, TEXT2.length, text2Bounds)
        backRectF.set((left + 30).toFloat(), (top + textBounds.height() * 2).toFloat(), (right - 30).toFloat() , (bottom - text2Bounds.height() * 2).toFloat())
        currentUsage = backRectF.right * 0.75f
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