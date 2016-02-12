package com.six.ui_anim.richEditor.span;

import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * Created by xxl on 16/2/12.
 */
public class RichURLSpan extends URLSpan {
    private int linkColor = 0;
    private boolean linkUnderline = true;

    public RichURLSpan(String url, int linkColor, boolean linkUnderline) {
        super(url);
        this.linkColor = linkColor;
        this.linkUnderline =  linkUnderline;
    }

    public RichURLSpan(Parcel src) {
        super(src);
        this.linkColor = src.readInt();
        this.linkUnderline = src.readInt() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(linkColor);
        dest.writeInt(linkUnderline ? 1 : 0);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(linkColor != 0 ? linkColor : ds.linkColor);
        ds.setUnderlineText(linkUnderline);
    }
}
