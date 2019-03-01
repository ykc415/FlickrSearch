package com.example.flickrsearch.ui.main.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.bumptech.glide.Glide
import com.example.flickrsearch.R
import com.example.flickrsearch.ui.main.PhotoData


@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MyPhoto @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : FrameLayout(context, attrs, defStyleAttr) {

    val imageView: AppCompatImageView
    val title: AppCompatTextView

    init {
        inflate(context, R.layout.photo, this)

        imageView = findViewById(R.id.imageView)
        title     = findViewById(R.id.title)
    }

    @ModelProp
    fun setData(data: PhotoData) {
        title.text = data.title

        Glide.with(imageView)
            .load(data.url)
            .centerCrop()
            .into(imageView)
    }

}