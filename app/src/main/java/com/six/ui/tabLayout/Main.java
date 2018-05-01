package com.six.ui.tabLayout;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-30.
 */

public interface Main {
    interface View{
        void showSurveyDialog();
        void setTitle(String title);
    }

    interface Presenter {
        void checkSurveyStatus();
    }
}
