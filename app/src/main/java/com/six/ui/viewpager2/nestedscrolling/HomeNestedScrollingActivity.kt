package com.six.ui.viewpager2.nestedscrolling

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.six.ui.R

/**
 * @author hellenxu
 * @date 2020-10-15
 * Copyright 2020 Six. All rights reserved.
 */
class HomeNestedScrollingActivity : AppCompatActivity(R.layout.act_home_nested_scrolling),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var contentPager: ViewPager2
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contentPager = findViewById(R.id.content_container)
        contentPager.isUserInputEnabled = false
        contentPager.adapter = ContentStateAdapter(this)

        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        contentPager.setCurrentItem(item.order, true)
        return true
    }

}