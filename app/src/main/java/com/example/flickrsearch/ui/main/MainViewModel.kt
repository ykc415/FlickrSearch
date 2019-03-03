package com.example.flickrsearch.ui.main

import com.airbnb.mvrx.*
import com.example.flickrsearch.data.Repository
import com.example.flickrsearch.data.dto.FlickrSearchResponse
import com.example.flickrsearch.ui.base.MvRxViewModel
import com.example.flickrsearch.utils.FlickrUrlParser
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

const val PHOTO_COUNT = 20

data class PhotoData(
    val title: String,
    val url: String
)

data class MainState(
    val currentKeyword: String = "Apple",

    /** We use this request to store the list of all photos. */
    val photos: List<PhotoData> = emptyList(),

    /** We use this Async to keep track of the state of the current network request. */
    val request: Async<FlickrSearchResponse> = Uninitialized

) : MvRxState

/**
 * initialState *must* be implemented as a constructor parameter.
 */
class MainViewModel(
    initialState: MainState,
    private val repository: Repository

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

            return MainViewModel(state, service)
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

    fun updateCurrentKeyword(keyword: String) {
        setState {
            MainState(currentKeyword = keyword)
        }
    }



}