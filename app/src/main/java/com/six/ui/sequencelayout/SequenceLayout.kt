package com.six.ui.sequencelayout

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-07-03.
 */

//TODO Way 2
class SequenceLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val nodes: List<SequenceNode> = ArrayList()

    init {


    }


}