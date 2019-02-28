package com.example.flickrsearch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrsearch.R
import com.example.flickrsearch.ui.main.model.ButtonModel_
import com.example.flickrsearch.ui.main.model.PhotoModel_
import com.example.flickrsearch.utils.extensions.observe
import com.example.flickrsearch.utils.extensions.withModels
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.viewModel


class MainFragment : Fragment() {

    val TAG = this::class.simpleName

    val viewModel: MainViewModel by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        photo_recyclerView.setItemSpacingDp(4)
    }


    private fun observeViewModel() {

        viewModel.run {
            observe(titleText) { title.text = it }

            observe(keywords) { words ->

                recyclerView.withModels {
                    words.forEachIndexed { index, s ->
                        ButtonModel_().id(index).text(s).clickListener { v ->
                            viewModel.keywordButtonClicked(s)
                        }.addTo(this)
                    }
                }
            }

            observe(photos) { datas ->
                photo_recyclerView.withModels {
                    datas.forEachIndexed { index, photoData ->
                        PhotoModel_().id(index).viewData(photoData).addTo(this)
                    }
                }
            }
        }

    }


}
