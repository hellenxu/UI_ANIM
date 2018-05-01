package com.six.ui.tabLayout;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-30.
 */

public class MainActivityPresenter implements Main.Presenter {
    private Main.View view;

    public void setView(Main.View view) {
        this.view = view;
    }

    @Override
    public void checkSurveyStatus() {
//        Random rd = new Random();
//        if (rd.nextBoolean()) {
            view.showSurveyDialog();
//        }

    }
}
