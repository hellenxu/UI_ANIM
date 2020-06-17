package com.six.ui.dropdown

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ListAdapter
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import com.six.ui.R

/**
 * @author hellenxu
 * @date 2020-06-16
 * Copyright 2020 Six. All rights reserved.
 */
class SixSpinner @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): AppCompatTextView(ctx, attrs, defStyleAttr), View.OnClickListener {

    private val popupWindow: ListPopupWindow by lazy {
        ListPopupWindow(ctx).apply { anchorView = this@SixSpinner }
    }

    private var listOpen = false
    private var unSelectedBg = R.drawable.spinner_unselected
    private var selectedBg = R.drawable.spinner_selected
    private var selectingBg = R.drawable.spinner_bg // TODO add a selecting background
    private var verticalOffset = 0
//    private var popupWindowWidth = AbsListView.LayoutParams.WRAP_CONTENT
//    private var popupWindowHeight = AbsListView.LayoutParams.WRAP_CONTENT
    private var windowPosition = PopupWindowPosition.BELOW_ANCHOR

    init {
        setOnClickListener(this)
        parseAttrs()
    }

    private fun parseAttrs() {

    }

    override fun onClick(v: View?) {
        background = if (!listOpen) {
            popupWindow.show()
            ContextCompat.getDrawable(context, selectingBg)
        } else {
            popupWindow.dismiss()
            ContextCompat.getDrawable(context, unSelectedBg)
        }

        listOpen = !listOpen
    }

    fun setAdapter(adapter: ListAdapter) {
        popupWindow.setAdapter(adapter)
    }

    fun setHintText(hint: String) {
        text = hint
    }

    fun setHintText(resId: Int) {
        setText(resId)
    }

    fun setPopupWindowWidth(width: Int) {
        popupWindow.width = width
    }

    fun setPopupWindowHeight(height: Int) {
        popupWindow.height = height
    }

    fun setPopupWindowPosition(pos: PopupWindowPosition) {
        windowPosition = pos
    }

    fun setVerticalOffset(offset: Int) {
        popupWindow.verticalOffset =
            when (windowPosition) {
                PopupWindowPosition.ABOVE_ANCHOR -> -(popupWindow.height + height + offset)
                PopupWindowPosition.BELOW_ANCHOR -> offset
            }
    }

    enum class PopupWindowPosition{
        ABOVE_ANCHOR,
        BELOW_ANCHOR
    }
}