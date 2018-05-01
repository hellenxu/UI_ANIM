package com.six.ui.tabLayout;

import java.util.Random;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-30.
 */

public class SurveyDialogPresenter implements Survey.Presenter {
    private Survey.View view;

    public void setSurveyView(Survey.View view) {
        this.view = view;
    }

    @Override
    public void handlePositive() {
        view.showThreeButtonDialog();
    }

    @Override
    public void handleNeutral() {

    }

    @Override
    public void handleNegative() {

    }
}
