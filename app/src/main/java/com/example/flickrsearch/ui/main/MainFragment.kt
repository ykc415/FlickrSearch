package com.example.flickrsearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.flickrsearch.R
import com.example.flickrsearch.utils.extensions.observe
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.viewModel


class MainFragment : Fragment() {

    val TAG = this::class.simpleName

    val viewModel: MainViewModel by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Log.e(TAG, "OnCreateView $this")
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()


        recyclerView.withModels {
            for (i in 0 until 100) {
                SimpleKotlinModel(i.toString()).id(i).addTo(this)
            }
        }

    }

    private fun observeViewModel() {

        viewModel.run {
            observe(count) {}

            observe(keywords) {
                Log.e(TAG, it.toString())


            }

        }
    }


}

fun EpoxyRecyclerView.withModels(buildModelsCallback: EpoxyController.() -> Unit) {
    setControllerAndBuildModels(object : EpoxyController() {
        override fun buildModels() {
            buildModelsCallback()
        }
    })
}