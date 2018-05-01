package com.six.ui;

import android.app.Application;
import android.content.Context;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-05-01.
 */

public class MyApplication extends Application {
    public static Context appCtx;

    @Override
    public void onCreate() {
        super.onCreate();
        appCtx = this;
    }
}
