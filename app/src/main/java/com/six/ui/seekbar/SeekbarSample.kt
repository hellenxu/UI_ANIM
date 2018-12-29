package com.six.ui.seekbar

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import com.six.ui.R
import com.six.ui.tabLayout.PageAdapter
import kotlinx.android.synthetic.main.act_seekbar.*

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-12-11.
 */
class SeekbarSample: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_seekbar)
//        slider2.setLimitRatio(.7f)
//        slider2.setUsedRatio(.05f)
//        slider2.setTotalLimit(12)

        tabs.addTab(tabs.newTab().setText(R.string.txt_index))
        tabs.addTab(tabs.newTab().setText(R.string.txt_travel))
        tabs.addTab(tabs.newTab().setText(R.string.txt_offers))
        tabs.addTab(tabs.newTab().setText(R.string.txt_details))

        vp.adapter = PageAdapter(supportFragmentManager, tabs.tabCount)
        vp.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab) {
                vp.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
            }
        })
    }

//    override fun onResume() {
//        super.onResume()
//        usage.start()
////        bar2.start()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        usage.stop()
////        bar2.start()
//    }

}