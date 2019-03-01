package com.example.flickrsearch.ui.main

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.airbnb.mvrx.*
import com.example.flickrsearch.R
import com.example.flickrsearch.data.Repository
import com.example.flickrsearch.data.dto.FlickrSearchResponse
import com.example.flickrsearch.ui.base.MvRxViewModel
import com.example.flickrsearch.ui.main.views.PhotoData
import com.example.flickrsearch.utils.FlickrUrlParser
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

typealias Keywords = List<String>

private const val PHOTO_COUNT = 20

data class MainState(
    val currentKeyword: String = "apple",

    /** We use this request to store the list of all jokes. */
    val photos: List<PhotoData> = emptyList(),

    /** We use this Async to keep track of the state of the current network request. */
    val request: Async<FlickrSearchResponse> = Uninitialized

) : MvRxState

/**
 * initialState *must* be implemented as a constructor parameter.
 */
class MainViewModel(
    initialState: MainState,
    val repository: Repository,
    val resources: Resources

) : MvRxViewModel<MainState>(initialState) {

    /**
     * If you implement MvRxViewModelFactory in your companion object, MvRx will use that to create
     * your ViewModel. You can use this to achieve constructor dependency injection with MvRx.
     *
     * @see MvRxViewModelFactory
     */

    companion object : MvRxViewModelFactory<MainViewModel, MainState> {

        override fun create(viewModelContext: ViewModelContext, state: MainState): MainViewModel {
            val service: Repository by viewModelContext.activity.inject()
            return MainViewModel(state, service, viewModelContext.activity.resources)
        }
    }

    init {
        fetchNextPage()
    }

    fun fetchNextPage() = withState { state ->
        if (state.request is Loading) return@withState

        repository
            .search(keyword = state.currentKeyword, page = if (state.photos.isEmpty()) 0 else state.photos.size / PHOTO_COUNT + 1)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(request = it,
                     photos  = photos + (it()?.photos?.photo?.map { rawData ->
                        PhotoData(
                             title = rawData.title,
                             url   = FlickrUrlParser.parse(rawData)
                        )
                } ?: emptyList()))
            }
    }



}