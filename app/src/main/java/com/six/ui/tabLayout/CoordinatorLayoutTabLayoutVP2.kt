package com.six.ui.tabLayout

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.six.ui.R
import com.six.ui.viewpager2.ApproachAdapter

/**
 * @author hellenxu
 * @date 2020-05-23
 * Copyright 2020 Six. All rights reserved.
 */
class CoordinatorLayoutTabLayoutVP2: FragmentActivity() {

    private lateinit var tabs: TabLayout
    private lateinit var vp: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_collapsing_tab_vp)

        tabs = findViewById(R.id.tabs)
        vp = findViewById(R.id.vp2)
        vp.adapter = ApproachAdapter(this)
        vp.setCurrentItem(3, true)

        val data = getData()

        TabLayoutMediator(tabs, vp) {tab, position ->
            tab.text = data[position].text
        }.attach()

    }

    private fun getData(): MutableList<TextView>{
        val data = mutableListOf<TextView>()

        for (i in 0..3) {
            data.add(TextView(this).apply { text = "Text$i" })
        }

        return data
    }
}