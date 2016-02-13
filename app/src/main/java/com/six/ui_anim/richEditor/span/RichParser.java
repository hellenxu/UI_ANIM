package com.six.ui_anim.richEditor.span;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by Hellen on 2016/2/13.
 */

public class RichParser {
    public static Spanned fromHtml(String source){
        return Html.fromHtml(source, null, new RichTagHandler());
    }
}
