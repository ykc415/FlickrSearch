package com.example.flickrsearch.ui.main

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.example.flickrsearch.R
import kotlinx.android.synthetic.main.view_header.view.*


@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class HeaderView(context: Context) : FrameLayout(context) {

    init {
        inflate(context, R.layout.view_header, this)
    }

    @TextProp
    fun setTitle(text: CharSequence) {
        textView.text = text
    }

    @TextProp
    fun setDescription(text: CharSequence) {
        description.text = text
    }

}