package com.example.flickrsearch.ui.main.views

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.example.flickrsearch.R


@EpoxyModelClass(layout = R.layout.photo)
abstract class PhotoModel : EpoxyModelWithHolder<PhotoModel.Holder>() {

    @EpoxyAttribute
    lateinit var viewData: PhotoData

    override fun bind(holder: Holder) {
        holder.title.text = viewData.title

        Glide.with(holder.imageView)
            .load(viewData.url)
            .centerCrop()
            .into(holder.imageView)
    }


    class Holder : EpoxyHolder() {

        lateinit var imageView: AppCompatImageView
        lateinit var title: AppCompatTextView

        override fun bindView(itemView: View) {
            imageView = itemView.findViewById(R.id.imageView)
            title     = itemView.findViewById(R.id.title)
        }
    }

}

data class PhotoData(
    val title: String,
    val url: String
)
