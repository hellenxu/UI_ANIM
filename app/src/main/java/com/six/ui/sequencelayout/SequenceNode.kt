package com.six.ui.sequencelayout

import android.content.Context
import android.util.TypedValue
import android.widget.TextView

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-07-03.
 */

//TODO Method 2
class SequenceNode(context: Context) {
    private val anchorTextView = TextView(context)
    private val titleTextView = TextView(context)
    private val subtitleTextView = TextView(context)

    private val ANCHOR_DEFAULT_TEXT_SIZE = 14f
    private val TITLE_DEFAULT_TEXT_SIZE = 18f
    private val SUBTITLE_DEFAULT_TEXT_SIZE = 16f

    init {
        anchorTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, ANCHOR_DEFAULT_TEXT_SIZE)
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TITLE_DEFAULT_TEXT_SIZE)
        subtitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SUBTITLE_DEFAULT_TEXT_SIZE)
    }

}