package com.six.ui_anim.richEditor.span;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.BulletSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import com.six.ui_anim.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxl on 16/2/9.
 */

public class RichText extends EditText implements TextWatcher {
    private int bulletColor;
    private int bulletRadius;
    private int bulletGapWidth;

    public RichText(Context context) {
        this(context, null);
    }

    public RichText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RichText);
        bulletColor = array.getColor(R.styleable.RichText_bulletColor, 0);
        bulletRadius = array.getDimensionPixelSize(R.styleable.RichText_bulletRadius, 0);
        bulletGapWidth = array.getDimensionPixelSize(R.styleable.RichText_bulletGapWidth, 0);
        array.recycle();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    public void bullet(boolean valid) {
        if (valid) {
            bulletValid();
        } else {
            bulletInvalid();
        }
    }

    protected void bulletValid() {
        String[] lines = TextUtils.split(getEditableText().toString(), "\n");
        for (int i = 0; i < lines.length; i++) {

            int lineStart = 0;
            for (int j = 0; j < i; j++) {
                lineStart = lineStart + lines[j].length() + 1;
            }
            int lineEnd = lineStart + lines[i].length();
            if (lineStart >= lineEnd) {
                continue;
            }

            int bulletStart = 0;
            int bulletEnd = 0;
            if (lineStart <= getSelectionStart() && getSelectionEnd() <= lineEnd) {
                bulletStart = lineStart;
                bulletEnd = lineEnd;
            } else if (getSelectionStart() <= lineStart && lineEnd <= getSelectionEnd()) {
                bulletStart = lineStart;
                bulletEnd = lineEnd;
            }

            if (bulletStart < bulletEnd) {
                getEditableText().setSpan(new RichBulletSpan(bulletColor, bulletRadius, bulletGapWidth), bulletStart, bulletEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    protected void bulletInvalid() {
        String[] lines = TextUtils.split(getEditableText().toString(), "\n");

        for(int i = 0; i < lines.length; i ++){

        }
    }

    protected boolean containBullet(){
        String[] lines = TextUtils.split(getEditableText().toString(), "\n");
        List<Integer> list = new ArrayList<>();

        for(int i = 0; i < lines.length; i ++){
            int lineStart = 0;
            for(int j = 0; j < i; j ++){
                lineStart = lineStart + lines[j].length() + 1;
            }
            int lineEnd = lineStart + lines[i].length();
            if(lineStart >= lineEnd){
                continue;
            }

            if(lineStart <= getSelectionStart() && getSelectionEnd() <= lineEnd){
                list.add(i);
            } else if(getSelectionStart() <= lineStart && lineEnd <= getSelectionEnd()){
                list.add(i);
            }
        }


        return true;
    }

    protected void switchToRichStyle(Editable editable, int start, int end) {
        BulletSpan[] bulletSpans = editable.getSpans(start, end, BulletSpan.class);
        for (BulletSpan span : bulletSpans) {
            int spanStart = editable.getSpanStart(span);
            int spanEnd = editable.getSpanEnd(end);
            spanEnd = 0 < spanEnd && spanEnd < editable.length() && editable.charAt(spanEnd) == '\n' ? spanEnd - 1 : spanEnd;
            editable.removeSpan(span);
            editable.setSpan(new RichBulletSpan(bulletColor, bulletRadius, bulletGapWidth), spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
