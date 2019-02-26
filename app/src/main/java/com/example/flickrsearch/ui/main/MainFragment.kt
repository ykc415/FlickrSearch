package com.example.flickrsearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flickrsearch.lithograph.ListItem
import com.example.flickrsearch.lithograph.ListSection
import com.example.flickrsearch.utils.extensions.observe
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.widget.Text
import org.koin.androidx.viewmodel.ext.viewModel

class MainFragment : Fragment() {

    val TAG = this::class.simpleName

    val viewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val componentContext = ComponentContext(context)

//        val component = ListItem.create(componentContext).build()

        val component = RecyclerCollectionComponent.create(componentContext)
            .disablePTR(true)
            .section(ListSection.create(SectionContext(componentContext)).build())
            .build()


        return LithoView.create(componentContext, component)

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