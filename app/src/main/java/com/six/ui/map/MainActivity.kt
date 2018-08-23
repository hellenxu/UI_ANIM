package com.six.ui.map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.six.ui.R

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-08-22.
 */
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_map_main)

        val fragment = MapFragment()

        supportFragmentManager.beginTransaction().replace(R.id.mapContainer, fragment).commit()
    }
}