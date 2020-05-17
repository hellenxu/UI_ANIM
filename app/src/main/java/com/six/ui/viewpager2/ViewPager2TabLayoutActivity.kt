package com.six.ui.viewpager2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.six.ui.R

/**
 * @author hellenxu
 * @date 2020-05-14
 * Copyright 2020 Six. All rights reserved.
 */
class ViewPager2TabLayoutActivity: AppCompatActivity() {

    private lateinit var tabs: TabLayout
    private lateinit var vp: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_vp2_tablayout)

        tabs = findViewById(R.id.tabs)
        vp = findViewById(R.id.vp2)
        vp.isUserInputEnabled = false // disable swiping
        vp.adapter = ApproachAdapter(this)


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