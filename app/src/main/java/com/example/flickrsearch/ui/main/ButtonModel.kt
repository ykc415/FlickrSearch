package com.example.flickrsearch.ui.main

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatButton
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.flickrsearch.R
import com.example.flickrsearch.utils.KotlinModel


@EpoxyModelClass(layout = R.layout.button)
abstract class ButtonModel : EpoxyModelWithHolder<Holder>() {

    @EpoxyAttribute
    lateinit var text: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener


    override fun bind(holder: Holder) {
        holder.button.text = text
        holder.button.setOnClickListener(clickListener)
    }
}

class Holder : EpoxyHolder() {

    lateinit var button: AppCompatButton

    override fun bindView(itemView: View) {
        button = itemView.findViewById(R.id.btn)
    }
}

