package com.six.ui.seekbar

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.animation.LinearInterpolator
import com.six.ui.R

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-12-11.
 */
class SixProgressbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), Animatable {

    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private val foregroundPaint: Paint = Paint(backgroundPaint)
    private val textPaint: Paint = Paint(backgroundPaint)
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

        val ta = context.obtainStyledAttributes(attrs, R.styleable.SixProgressbar)
        //paint init
        backgroundPaint.color = ta.getColor(R.styleable.SixProgressbar_bg_color, Color.parseColor("#FFF0F5"))
        backgroundPaint.style = Paint.Style.FILL

        foregroundPaint.color = ta.getColor(R.styleable.SixProgressbar_fg_color, Color.parseColor("#FFB6C1"))
        foregroundPaint.style = Paint.Style.FILL

        textPaint.color = Color.BLACK
        textPaint.textSize = ta.getDimension(R.styleable.SixProgressbar_txtSize, resources.getDimension(R.dimen.txt_size))

        ta.recycle()

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

    private fun dp2px(dp: Float): Float {
        return dp * metrics.density
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawRoundRect(backRectF, roundCornerRadius,roundCornerRadius, backgroundPaint)
        canvas.clipRect(currentRight, backRectF.top, currentRight + roundCornerRadius, backRectF.bottom, Region.Op.DIFFERENCE)
        canvas.drawRoundRect(backRectF.left, backRectF.top, currentRight + roundCornerRadius, backRectF.bottom, roundCornerRadius,roundCornerRadius, foregroundPaint)

        if(isLoadingFinished) {
            canvas.drawText(TEXT, currentRight - textBounds.width() / 2 , backRectF.top  - dp2px(5f), textPaint)
            canvas.drawText(TEXT2, backRectF.left , backRectF.bottom + text2Bounds.height() + dp2px(5f), textPaint)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        textPaint.getTextBounds(TEXT, 0, TEXT.length, textBounds)
        textPaint.getTextBounds(TEXT2, 0, TEXT2.length, text2Bounds)
        val drawingRect = Rect()
        getDrawingRect(drawingRect)
//        backRectF.set(left + dp2px(10f), top + textBounds.height() + dp2px(10f), right - dp2px(10f) , bottom - text2Bounds.height() - dp2px(10f))
        println("xxl-left:$left; rect-left: ${drawingRect.left}")
        println("xxl-right:$right; rect-right: ${drawingRect.right}")
        println("xxl-top:$top; rect-top: ${drawingRect.top}")
        println("xxl-bottom:$bottom; rect-bottom: ${drawingRect.bottom}")

        backRectF.set(drawingRect.left + dp2px(10f), drawingRect.top + textBounds.height() + dp2px(10f), drawingRect.right - dp2px(10f) , drawingRect.bottom - text2Bounds.height() - dp2px(10f))
        currentUsage = backRectF.right * 0.75f

        loadingAnimator.start()

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


    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    //TODO increase touching sensitivity; ACTION_MOVE
    override fun onTouchEvent(event: MotionEvent): Boolean {
        performClick()

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

    override fun onPopulateAccessibilityEvent(event: AccessibilityEvent) {
        super.onPopulateAccessibilityEvent(event)

        val type = event.eventType
        when (type) {
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                event.text.add("meter")
            }

            AccessibilityEvent.TYPE_VIEW_SELECTED -> {
                event.text.add(TEXT)
                event.text.add(TEXT2)
            }
            else -> {
                event.text.add("others")
            }
        }
    }

}