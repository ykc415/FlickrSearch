package com.example.flickrsearch.ui.main

import androidx.appcompat.widget.AppCompatButton
import com.airbnb.epoxy.AutoModel
import com.example.flickrsearch.R
import com.example.flickrsearch.utils.KotlinModel

data class SimpleKotlinModel(
        val text: String
) : KotlinModel(R.layout.button) {


    val button by bind<AppCompatButton>(R.id.btn)

    override fun bind() {
        button.setText(text)
    }
}