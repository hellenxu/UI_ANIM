package com.six.ui.viewpager2

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import com.six.ui.R

/**
 * @author hellenxu
 * @date 2020-05-22
 * Copyright 2020 Six. All rights reserved.
 */
class SimpleSurvey @JvmOverloads constructor(
    @NonNull ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(ctx, attrs, defStyleAttr) {

    init {
        View.inflate(ctx, R.layout.simple_survey, this)
    }
}