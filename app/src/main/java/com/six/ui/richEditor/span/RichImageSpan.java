package com.six.ui.richEditor.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.style.ImageSpan;

/**
 * Created by xxl on 16/2/21.
 */

public class RichImageSpan extends ImageSpan {
    public RichImageSpan(Context context, Bitmap b) {
        super(context, b);
    }

    public RichImageSpan(Context context, Bitmap b, int verticalAlignment) {
        super(context, b, verticalAlignment);
    }
}
