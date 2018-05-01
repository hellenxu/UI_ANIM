package com.six.ui.tabLayout;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-30.
 */

public interface Survey {

    interface View {
        void showThreeButtonDialog();
        void showTwoButtonDialog();
    }

    interface Presenter {
        void handlePositive();
        void handleNeutral();
        void handleNegative();
    }
}