package com.six.ui.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

import com.six.ui_anim.R;

/**
 * @author hellenxu
 * @date 2015/12/9
 * Copyright 2015 Six. All rights reserved.
 */
public class BounceScrollViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.act_bounce_scrollview);
        setContentView(R.layout.act_bounce_sv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
