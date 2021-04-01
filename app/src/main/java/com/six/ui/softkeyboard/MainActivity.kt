package com.six.ui.softkeyboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.six.ui.R

/**
 * @author hellenxu
 * @date 2021-03-31
 * Copyright 2021 Six. All rights reserved.
 */
class MainActivity: AppCompatActivity(R.layout.act_keyboard_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.frag_content, FragmentWithTextInput()).commit()
    }
}