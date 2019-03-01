package com.example.flickrsearch.ui.base.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelView
import com.example.flickrsearch.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class LoadingRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.loading_row, this)
    }
}