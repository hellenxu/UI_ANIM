package com.six.ui.viewpager2

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView

class ManuallyScrollView @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : NestedScrollView(context, attrs, defStyleAttr) {
    override fun requestChildFocus(child: View?, focused: View?) {
        // do nothing literally
    }
}