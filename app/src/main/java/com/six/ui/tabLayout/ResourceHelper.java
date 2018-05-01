package com.six.ui.tabLayout;


import android.content.Context;

import com.six.ui.MyApplication;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-05-01.
 */

public class ResourceHelper {

    static Context appCtx = MyApplication.appCtx;

    public static String getString(int resId) {
        return appCtx.getString(resId);
    }
}
