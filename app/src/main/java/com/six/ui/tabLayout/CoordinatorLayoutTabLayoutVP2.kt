package com.six.ui.tabLayout

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.six.ui.R
import com.six.ui.viewpager2.ApproachAdapter
import kotlin.math.abs

/**
 * @author hellenxu
 * @date 2020-05-23
 * Copyright 2020 Six. All rights reserved.
 */
class CoordinatorLayoutTabLayoutVP2: FragmentActivity() {

    private lateinit var tabs: TabLayout
    private lateinit var vp: ViewPager2
    private lateinit var abl: AppBarLayout
    private lateinit var toolbar: Toolbar

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

        toolbar = findViewById(R.id.toolbar)
        abl = findViewById(R.id.app_bar)
        abl.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val completedCollapsing = abs(verticalOffset) >= appBarLayout.totalScrollRange
//            if (completedCollapsing) {
//                toolbar.navigationIcon = getDrawable(R.drawable.ic_arrow_back_24dp)
//            } else {
//                toolbar.navigationIcon = null
//            }
        })

        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.cancel -> {
                    finish()
                    true
                }
                else -> { false }
            }
        }
    }

    private fun getData(): MutableList<TextView>{
        val data = mutableListOf<TextView>()

        for (i in 0..3) {
            data.add(TextView(this).apply { text = "Text$i" })
        }

        return data
    }
}