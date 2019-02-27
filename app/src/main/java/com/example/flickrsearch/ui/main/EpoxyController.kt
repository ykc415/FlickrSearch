package com.example.flickrsearch.ui.main

import com.airbnb.epoxy.TypedEpoxyController


class EpoxyController : TypedEpoxyController<List<Sample>>() {

    override fun buildModels(data: List<Sample>?) {

        data?.forEach {
            headerView {
                id("header")
                title(it.title)
                description(it.description)
            }
        }
    }

}