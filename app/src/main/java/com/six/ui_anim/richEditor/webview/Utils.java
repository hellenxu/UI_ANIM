package com.six.ui_anim.richEditor.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * @author hellenxu
 * @date 2016/1/28
 * Copyright 2016 Six. All rights reserved.
 */
public class Utils {

    public static Bitmap decodeResource(Context ctx, int resid) {
        return BitmapFactory.decodeResource(ctx.getResources(), resid);
    }

    public static String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrOs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrOs);
        byte[] bytes = byteArrOs.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
