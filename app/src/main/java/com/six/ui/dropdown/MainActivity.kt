package com.six.ui.dropdown

import android.app.Activity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import com.six.ui.R

/**
 * @author hellenxu
 * @date 2020-06-02
 * Copyright 2020 Six. All rights reserved.
 */
class MainActivity: Activity() {
    private lateinit var tvAnchor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_drop_down)

        tvAnchor = findViewById(R.id.tv_anchor)

        val popupList = ListPopupWindow(this)
        popupList.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_item, getData()))
        popupList.anchorView = tvAnchor
        popupList.width = AbsListView.LayoutParams.WRAP_CONTENT //drop down with is the same as anchor view
        popupList.height = 100*3
        popupList.verticalOffset = -(40 * 3 + 100*3) + 30

        popupList.setOnItemClickListener { parent, view, position, id ->
            println("xxl-selected: $position")
            popupList.dismiss()
        }
        popupList.setOnDismissListener {
            tvAnchor.background = ContextCompat.getDrawable(this, R.drawable.spinner_unselected)
        }


        tvAnchor.setOnClickListener {
            tvAnchor.background = ContextCompat.getDrawable(this, R.drawable.spinner_selected)
            popupList.show()
        }

    }

    private fun getData(): List<String> {
        val data = mutableListOf<String>()
        for (i in 0..60) {
            data.add("item No.$i")
        }

        return data
    }
}