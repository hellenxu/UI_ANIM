package com.six.ui.tabLayout;

import android.text.TextUtils;

import com.six.ui.R;


/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-30.
 */

public class MainActivityPresenter implements Main.Presenter {
    private Main.View view;
    private int a;

    public void setView(Main.View view) {
        this.view = view;
    }

    public void setBool(int a){
        this.a = a;
    }

    @Override
    public void checkSurveyStatus() {
        if (checkState()) {
            view.showSurveyDialog();
        }
    }

    boolean checkState() {
        return a % 2 == 0;
    }

    public void piaoLin(){
        view.setTitle(ResourceHelper.getString(R.string.account_online));
        lookAtLin();
    }

    public void lookAtLin(){

    }

    public boolean checkEmpty(String content) {
        return TextUtils.isEmpty(content);
    }
}
