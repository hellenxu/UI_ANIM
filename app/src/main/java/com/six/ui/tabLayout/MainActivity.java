package com.six.ui.tabLayout;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.six.ui.R;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-19.
 */

public class MainActivity extends AppCompatActivity implements Main.View{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tab);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_index));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_travel));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_offers));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_details));

        final ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        MainActivityPresenter presenter = new MainActivityPresenter();
        presenter.setView(this);
        presenter.checkSurveyStatus();
    }

    @Override
    public void showSurveyDialog() {
        SurveyDialog dialog = new SurveyDialog(getSupportFragmentManager());
        dialog.showTwoButtonDialog();
    }

    @Override
    public void setTitle(String title) {
        Log.d("xxl", title);
    }
}
