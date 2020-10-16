package com.six.ui.viewpager2.nestedscrolling

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.six.ui.tabLayout.DetailsFragment
import com.six.ui.tabLayout.IndexFragment
import com.six.ui.tabLayout.TravelFragment

/**
 * @author hellenxu
 * @date 2020-10-15
 * Copyright 2020 Six. All rights reserved.
 */
class ContentStateAdapter(act: FragmentActivity): FragmentStateAdapter(act) {
    private val fragments = mutableListOf<Fragment>()

    init {
        fragments.addAll(listOf(TravelFragment(), IndexFragment(), DetailsFragment()))
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}