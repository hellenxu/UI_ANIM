package com.six.ui_anim.sample.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author hellenxu
 * @date 2016/3/14
 * Copyright 2016 Six. All rights reserved.
 */
public class LayerView extends View {
    private Paint mPaint;

    public LayerView(Context context) {
        this(context, null);
    }

    public LayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(150, 150, 100, mPaint);

        canvas.saveLayerAlpha(0, 0, 280, 300, 120, Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(200, 200, 100, mPaint);
        canvas.restore();
    }
}
