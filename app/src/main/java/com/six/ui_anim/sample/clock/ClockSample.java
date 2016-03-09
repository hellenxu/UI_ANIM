package com.six.ui_anim.sample.clock;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.six.ui_anim.R;

/**
 * @author hellenxu
 * @date 2016/3/9
 * Copyright 2016 Six. All rights reserved.
 */
public class ClockSample extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_clock);
        init();
    }

    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.tl_header);
        mToolbar.setNavigationIcon(R.drawable.ic_launcher);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("Clock Demo");
        setSupportActionBar(mToolbar);
    }

}
