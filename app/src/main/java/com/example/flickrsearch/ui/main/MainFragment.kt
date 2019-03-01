package com.example.flickrsearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.fragmentViewModel
import com.example.flickrsearch.R
import com.example.flickrsearch.ui.base.BaseFragment
import com.example.flickrsearch.ui.base.MvRxEpoxyController
import com.example.flickrsearch.ui.base.simpleController
import com.example.flickrsearch.ui.base.views.basicRow
import com.example.flickrsearch.ui.base.views.loadingRow
import com.example.flickrsearch.ui.base.views.marquee
import com.example.flickrsearch.utils.extensions.observe
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
                title("Dad Jokes")
            }

            state.photos.forEachIndexed { index, photo ->
                basicRow {
                    id(index)
                    title(photo.title)
                    clickListener { _ ->
                        Snackbar.make(this@MainFragment.requireView(), "Hello", Snackbar.LENGTH_INDEFINITE).show()
                    }
                }
            }

            loadingRow {
                // Changing the ID will force it to rebind when new data is loaded even if it is
                // still on screen which will ensure that we trigger loading again.
                id("loading${state.photos.size}")
                onBind { _, _, _ -> viewModel.fetchNextPage() }
            }
        }

}
