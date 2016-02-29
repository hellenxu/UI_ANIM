package com.six.ui_anim.creditCardView;

import android.text.TextUtils;

import com.six.ui_anim.R;

/**
 * @author hellenxu
 * @date 2016/2/29
 * Copyright 2016 Six. All rights reserved.
 */
public class CardSelector {
    public int mResCardBgId;
    public int mResChipOuterId;
    public int mResChipInnerId;
    public int mResCenterImgId;
    public int mResLogoId;
    public static final CardSelector VISA = new CardSelector(R.drawable.bg_card_purple, R.drawable.chip, R.drawable.chip_inner, android.R.color.transparent, R.drawable.ic_billing_visa_logo);
    public static final CardSelector MASTER = new CardSelector(R.drawable.bg_card_pink, R.drawable.chip_yellow, R.drawable.chip_yellow_inner, android.R.color.transparent, R.drawable.ic_billing_mastercard_logo);
    public static final CardSelector AMEX = new CardSelector(R.drawable.bg_card_green, android.R.color.transparent, android.R.color.transparent, R.drawable.img_amex_center_face, R.drawable.ic_billing_amex_logo);
    public static final CardSelector DEFAULT = new CardSelector(R.drawable.bg_card_default, R.drawable.chip, R.drawable.chip_inner, android.R.color.transparent, android.R.color.transparent);
    public static final String AMEX_PREFIX = "3";

    public CardSelector(int drawableCard, int drawableChipOuter, int drawableChipInner, int drawableCenterImage, int logoId) {
        this.mResCardBgId = drawableCard;
        this.mResChipOuterId = drawableChipOuter;
        this.mResChipInnerId = drawableChipInner;
        this.mResCenterImgId = drawableCenterImage;
        this.mResLogoId = logoId;
    }

    public static CardSelector selectCard(char cardFirstChar) {
        switch (cardFirstChar) {
            case '3':
                return AMEX;
            case '4':
                return VISA;
            case '5':
                return MASTER;
            default:
                return DEFAULT;
        }
    }

    public static CardSelector selectCard(String cardNumber) {
        if (!TextUtils.isEmpty(cardNumber) && cardNumber.length() >= 3) {
            CardSelector selector = selectCard(cardNumber.charAt(0));

            if (cardNumber.startsWith(AMEX_PREFIX)) {
                return AMEX;
            }

            if (selector != DEFAULT) {
                int drawables[] = {R.drawable.bg_card_pink, R.drawable.bg_card_purple, R.drawable.bg_card_green};
                int chips[] = {R.drawable.chip, R.drawable.chip_yellow, android.R.color.transparent};
                int chipInners[] = {R.drawable.chip_inner, R.drawable.chip_inner_yellow, android.R.color.transparent};
                int hash = Math.abs(cardNumber.substring(0, 3).hashCode());
                int bgIndex = hash % drawables.length;
                int chipIndex = hash % chips.length;
                selector.mResCardBgId = drawables[bgIndex];
                selector.mResChipOuterId = chips[chipIndex];
                selector.mResChipInnerId = chipInners[chipIndex];
                return selector;
            }
        }
        return DEFAULT;
    }
}
