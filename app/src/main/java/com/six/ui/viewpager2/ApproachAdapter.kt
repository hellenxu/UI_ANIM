package com.six.ui.viewpager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.six.ui.tabLayout.DetailsFragment
import com.six.ui.tabLayout.IndexFragment
import com.six.ui.tabLayout.OffersFragment
import com.six.ui.tabLayout.TravelFragment

/**
 * @author hellenxu
 * @date 2020-05-17
 * Copyright 2020 Six. All rights reserved.
 */
class ApproachAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    private val fragments = arrayListOf(
        IndexFragment(),
        DetailsFragment(),
        OffersFragment(),
        TravelFragment()
    )


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


}