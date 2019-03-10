package com.example.flickrsearch.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.MvRxViewModelStore

abstract class BaseFragment: Fragment(), MvRxView {

    protected val epoxyController by lazy { epoxyController() }

    override val mvrxViewModelStore by lazy { MvRxViewModelStore(viewModelStore) }

    override fun onCreate(savedInstanceState: Bundle?) {
        mvrxViewModelStore.restoreViewModels(this, savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    /**
     * Provide the EpoxyController to use when building models for this Fragment.
     * Basic usages can simply use [simpleController]
     */
    abstract fun epoxyController(): MvRxEpoxyController

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvrxViewModelStore.saveViewModels(outState)
        epoxyController.onSaveInstanceState(outState)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        invalidate()
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
    }

}