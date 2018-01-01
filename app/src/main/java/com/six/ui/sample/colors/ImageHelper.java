package com.six.ui.sample.colors;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * @author hellenxu
 * @date 2016/3/14
 * Copyright 2016 Six. All rights reserved.
 */
public class ImageHelper {

    public static Bitmap handleImageEffect(Bitmap bm, float hue, float saturation, float lum) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);
        hueMatrix.setRotate(1, hue);
        hueMatrix.setRotate(2, hue);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        ColorMatrix imgMatrix = new ColorMatrix();
        imgMatrix.postConcat(hueMatrix);
        imgMatrix.postConcat(saturationMatrix);
        imgMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imgMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);

        return bmp;
    }
}
