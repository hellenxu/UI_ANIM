package com.six.ui.recyclerview.transAds;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.six.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2017-11-01.
 */

public class TransparentAdActivity extends Activity {
    private static final String content = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_transparent_ad);
        RecyclerView rvContent = (RecyclerView) findViewById(R.id.rv_ad);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            if(i % 7 != 0) {
                data.add(String.format("item: %d \n" + getString(R.string.content), i));
            } else {
                data.add("item: " + i);
            }
        }

        TransparentAdapter adapter = new TransparentAdapter(this, data);
        rvContent.setAdapter(adapter);
    }
}
