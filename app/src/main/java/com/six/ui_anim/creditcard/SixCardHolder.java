package com.six.ui_anim.creditcard;

/**
 * @author hellenxu
 * @date 2016/3/4
 * Copyright 2016 Six. All rights reserved.
 */
public class SixCardHolder {
    public int bgColor;
    public int centerLogoId;
    public int frontRightLogoId;
    public int backBottomLogoId;
    public String ccNumSample;
    public String holderSample;
    public String expirySample;
    public String cvvSample;


    private SixCardHolder(int bgColor, int centerLogoId, int frontRightLogoId, int backLogoId, String ccNum, String holder, String expiry, String cvv) {
        this.bgColor = bgColor;
        this.centerLogoId = centerLogoId;
        this.frontRightLogoId = frontRightLogoId;
        this.backBottomLogoId = backLogoId;
        this.ccNumSample = ccNum;
        this.holderSample = holder;
        this.expirySample = expiry;
        this.cvvSample = cvv;
    }

    //TODO
    public static SixCardHolder selectCard(String cardNumber) {
        return null;
    }
}
