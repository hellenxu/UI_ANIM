package com.six.ui.support

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import com.six.ui.R
import com.six.ui.tabLayout.PageAdapter
import kotlinx.android.synthetic.main.act_bottom_nav.*

/**
 * @CopyRight six.ca
 * Created by Heavens on 2019-01-07.
 */
class BottomNaviActivity: AppCompatActivity() {

    private val onNavItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {


        when(it.itemId) {
            R.id.nav_home -> {
                vp.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_pet -> {
                vp.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_settings -> {
                vp.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    private lateinit var currentItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_bottom_nav)

        vp.adapter = PageAdapter(supportFragmentManager, navMenu.menu.size())
        println("xxl-children: ${navMenu.menu.size()}")
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentItem = navMenu.menu.getItem(position)
                currentItem.setChecked(true)
            }

        })

        //remove swiping of viewpager
        vp.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })
        navMenu.setOnNavigationItemSelectedListener(onNavItemSelectedListener)

    }

}