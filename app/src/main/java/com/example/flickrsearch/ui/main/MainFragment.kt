package com.example.flickrsearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.fragmentViewModel
import com.example.flickrsearch.R
import com.example.flickrsearch.ui.base.*
import com.example.flickrsearch.ui.base.views.loadingRow
import com.example.flickrsearch.ui.base.views.marquee
import com.example.flickrsearch.ui.main.views.MyButtonModel_
import com.example.flickrsearch.ui.main.views.myPhoto
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : BaseFragment() {

    val TAG = this::class.simpleName

    /**
     * This will get or create a new ViewModel scoped to this Fragment. It will also automatically
     * subscribe to all state changes and call [invalidate] which we have wired up to
     * call [buildModels] in [BaseFragment].
     */
    private val viewModel: MainViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.fetchNextPage()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_base_mvrx, container, false)

        /**
         * Use viewModel.subscribe to listen for changes. The parameter is a shouldUpdate
         * function that is given the old state and new state and returns whether or not to
         * call the subscriber. onSuccess, onFail, and propertyWhitelist ship with MvRx.
         */
        viewModel.asyncSubscribe(MainState::request, onFail = { error ->
            Snackbar.make(view, "Jokes request failed.", Snackbar.LENGTH_INDEFINITE)
                .show()
            Log.w(TAG, "Jokes request failed", error)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.setController(epoxyController)
    }

    override fun invalidate() {
        recyclerView.requestModelBuild()
    }

    override fun epoxyController(): MvRxEpoxyController =
        simpleController(viewModel) { state ->

            marquee {
                id("marquee")
                title(state.currentKeyword)
            }

            carousel {
                id("carousel")
                withModelsFrom(resources.getStringArray(R.array.keywords).toList()) {
                    MyButtonModel_()
                        .id(it)
                        .buttonText(it)
                        .clickListener { _ ->
                            viewModel.updateCurrentKeyword(it)
                        }
                }
            }

            state.photos.forEachIndexed { index, photo ->
                myPhoto {
                    id(index)
                    data(photo)
                }
            }

            loadingRow {
                // Changing the ID will force it to rebind when new data is loaded even if it is
                // still on screen which will ensure that we trigger loading again.
                id("loading${state.photos.size}")
                onBind { _, _, _ ->  viewModel.fetchNextPage() }
            }
        }

}
