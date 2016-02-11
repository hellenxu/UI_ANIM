package com.six.ui_anim.richEditor.span;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.BulletSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import com.six.ui_anim.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xxl on 16/2/9.
 */

public class RichText extends EditText implements TextWatcher {
    private int bulletColor;
    private int bulletRadius;
    private int bulletGapWidth;
    private boolean historyEnable = true;
    private int historySize = 100;
    private int linkColor = 0;
    private boolean linkUnderline = true;
    private int quoteColor = 0;
    private int quoteStripeWidth = 0;
    private int quoteGapWidth = 0;

    private List<Editable> historyList = new LinkedList<>();
    private boolean isHistoryWorking = false;
    private int historyCursor = 0;

    private SpannableStringBuilder inputBefore;
    private Editable inputLast;

    public static final int FORMAT_BOLD = 0X01;
    public static final int FORMAT_ITALIC = 0X02;
    public static final int FORMAT_UNDERLINED = 0X03;
    public static final int FORMAT_STRIKETHROUGH = 0X04;
    public static final int FORMAT_BULLET = 0X05;
    public static final int FORMAT_QUOTE = 0X06;
    public static final int FORMAT_LINK = 0X07;

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

        if(historyEnable && historySize <= 0){
            throw new IllegalArgumentException("historySize must > 0");
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addTextChangedListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if(!historyEnable || isHistoryWorking){
            return;
        }

        inputBefore = new SpannableStringBuilder(s);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!historyEnable || isHistoryWorking){
            return;
        }
        inputLast = new SpannableStringBuilder(s);
        if(s != null && s.toString().equals(inputBefore.toString())){
            return;
        }
        if(historyList.size() >= historySize){
            historyList.remove(0);
        }

        historyList.add(inputBefore);
        historyCursor = historyList.size();
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
    //do nothing
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

        for(Integer i : list){
            if(!containBullet(i)){
                return false;
            }
        }

        return true;
    }

    protected boolean containBullet(int index){
        String[] lines = TextUtils.split(getEditableText().toString(), "\n");
        if(index < 0 || index >= lines.length){
            return false;
        }

        int start = 0;
        for(int i = 0; i < index; i ++){
            start = start + lines[i].length() + 1;
        }

        int end = start + lines[index].length();
        if(start >= end){
            return false;
        }

        BulletSpan[] spans = getEditableText().getSpans(start, end, BulletSpan.class);
        return spans.length > 0;
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

    public boolean contains(int format){
        switch (format){
            case FORMAT_BULLET:
                return containBullet();
            default:
                return false;
        }
    }
}
