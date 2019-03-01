package com.example.flickrsearch.ui.main.views

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.example.flickrsearch.R


@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class MyButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : FrameLayout(context, attrs, defStyleAttr) {

    val button: Button

    init {
        inflate(context, R.layout.button, this)
        button = findViewById(R.id.btn)
    }

    @TextProp
    fun setButtonText(text: CharSequence) {
        button.text = text
    }

    @CallbackProp // Use this annotation for click listeners or other callbacks.
    fun clickListener(listener : OnClickListener?) {
        button.setOnClickListener(listener)
    }

}