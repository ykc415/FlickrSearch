package com.example.flickrsearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flickrsearch.R
import com.example.flickrsearch.utils.extensions.observe
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