package com.six.ui_anim.creditCardView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.six.ui_anim.R;

/**
 * @author hellenxu
 * @date 2016/2/29
 * Copyright 2016 Six. All rights reserved.
 */

public class CreditCardView extends FrameLayout {
    private static final int TEXTVIEW_CARD_HOLDER_ID = R.id.tv_card_holder;
    private static final int TEXTVIEW_CARD_EXPIRY_ID = R.id.tv_expire_date;
    private static final int TEXTVIEW_CARD_NUMBER_ID = R.id.tv_front_card_num;
    private static final int TEXTVIEW_CARD_CVV_ID = R.id.tv_cvv;
    private static final int FRONT_CARD_ID = R.id.front_card_container;
    private static final int BACK_CARD_ID = R.id.back_card_container;
//    private static final int FRONT_CARD_OUTLINE_ID = R.id.front_card_outline;
//    private static final int BACK_CARD_OUTLINE_ID = R.id.back_card_outline;

    public CreditCardView(Context context) {
        this(context, null);
    }

    public CreditCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreditCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TextView tvCardNum = new TextView(context);
    }

    interface ICustomCardSelector {
        CardSelector getCardSelector(String cardNumber);
    }
}
