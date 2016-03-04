package com.six.ui_anim.creditcard;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.six.ui_anim.R;

/**
 * @author hellenxu
 * @date 2016/3/4
 * Copyright 2016 Six. All rights reserved.
 */
public class SixCreditCardView extends FrameLayout{
    private String mCCNumber;
    private String mCCHolder;
    private String mCCExpiry;
    private int mCVV;
    private int mSide;

    private static final int DEFAULT_FRONT_CARD_ID = R.id.rlay_front_container;
    private static final int DEFAULT_BACK_CARD_ID = R.id.rlay_back_container;
    private static final int DEFAULT_CC_NUMBER = R.string.card_num_hint;
    private static final int DEFAULT_CC_HOLDER = R.string.card_holder_name_sample;
    private static final int DEFAULT_CC_EXPIRY = R.string.expiry_sample;
    private static final int DEFAULT_CC_CVV = 0;
    private static final int FRONT = 1;
    private static final int BACK = 0;

    public SixCreditCardView(Context context) {
        this(context, null);
    }

    public SixCreditCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SixCreditCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cc_back, this, true);
        inflater.inflate(R.layout.cc_front, this, true);

        TypedArray ta = context.obtainStyledAttributes(R.styleable.SixCC);
        mCCNumber = ta.getString(R.styleable.SixCC_number) != null ? ta.getString(R.styleable.SixCC_number) : context.getString(DEFAULT_CC_NUMBER);
        mCCHolder = ta.getString(R.styleable.SixCC_card_holder) != null ? ta.getString(R.styleable.SixCC_card_holder) : context.getString(DEFAULT_CC_HOLDER);
        mCCExpiry = ta.getString(R.styleable.SixCC_card_expiry) != null ? ta.getString(R.styleable.SixCC_card_expiry) : context.getString(DEFAULT_CC_EXPIRY);
        mCVV = ta.getInt(R.styleable.SixCC_card_cvv, DEFAULT_CC_CVV);
        mSide = ta.getInt(R.styleable.SixCC_side, FRONT);
        ta.recycle();

        paintCC();
    }

    //TODO
    private void paintCC(){

    }
}
