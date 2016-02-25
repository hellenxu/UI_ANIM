package com.six.ui_anim.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;

/**
 * @author hellenxu
 * @date 2016/2/25
 * Copyright 2016 Six. All rights reserved.
 */
public class CreditCardSample extends Activity {
    private static final String TAG = CreditCardSample.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tvInputCreditCard = new TextView(this);
        tvInputCreditCard.setText("create a new credit card");
        tvInputCreditCard.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        setContentView(tvInputCreditCard);

        tvInputCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CreditCardSample.this, CardEditActivity.class);
                startActivityForResult(it, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            String cardHolderName = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            Log.d(TAG, "cardHolderName: " + cardHolderName);
        }
    }
}
