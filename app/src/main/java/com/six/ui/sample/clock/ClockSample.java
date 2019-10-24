package com.six.ui.sample.clock;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.six.ui.R;

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
