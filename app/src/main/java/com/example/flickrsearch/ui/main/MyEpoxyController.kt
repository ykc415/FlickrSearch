package com.example.flickrsearch.ui.main

import android.util.Log
import com.airbnb.epoxy.TypedEpoxyController


class MyEpoxyController : TypedEpoxyController<List<String>>() {

    val TAG = this::class.java.simpleName

    override fun buildModels(data: List<String>?) {

        Log.e(TAG, "buildModels ${data.toString()}")

        data?.forEach {
            SimpleKotlinModel(it).addTo(this)
        }
    }

}