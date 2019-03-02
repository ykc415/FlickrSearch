package com.example.flickrsearch

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.example.flickrsearch.data.Repository
import com.example.flickrsearch.ui.main.MainViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.ClassRule
import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.example.flickrsearch.data.dto.FlickrPhoto
import com.example.flickrsearch.data.dto.FlickrPhotoPage
import com.example.flickrsearch.data.dto.FlickrSearchResponse
import com.example.flickrsearch.ui.main.MainState
import com.example.flickrsearch.ui.main.PhotoData
import com.nhaarman.mockitokotlin2.withSettings
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import org.junit.Test
import org.junit.Assert.*
import java.lang.Exception


class MainViewModelTest {

    companion object {

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }

    private val repository: Repository = mock()

    private lateinit var viewModel: MainViewModel


    @Before
    fun setup() {

    }

    @Test
    fun fetchNextPage_success() {

        // use subject to be able to verify the loading state before any emissions
        val photosSubject = SingleSubject.create<FlickrSearchResponse>()
        whenever(repository.search("test", 0)).thenReturn(photosSubject)

        // given the viewmodel with default state
        viewModel = MainViewModel(MainState(currentKeyword = "test"), repository)

        // verify that tasks were requested from the repository
        verify(repository).search("test", 0)

        withState(viewModel) { assertTrue(it.request is Loading) }

        val response = FlickrSearchResponse(
            photos = FlickrPhotoPage(
                page = 0,
                photo = listOf(
                    FlickrPhoto(
                        id = "id",
                        owner = "owner",
                        secret = "secret",
                        server = "server",
                        farm = -1,
                        title = "title",
                        url = "url"
                    )
                ),
                perpage = 20
            ),
            stat = "ok"
        )

        // new emission from the data source happened
        photosSubject.onSuccess(response)

        // verify that tasks request was successful and the photo list is present
        withState(viewModel) {
            assertTrue(it.request is Success)
            assertTrue(it.photos.size == 1)
        }

        // verify that loading state has changed to false after the stream is completed


    }

    @Test
    fun fetchNextPage_failed() {
        whenever(repository.search("test", 0)).thenReturn(Single.error(Exception("Socket Timeout")))

        // given the viewmodel with default state
        viewModel = MainViewModel(MainState(currentKeyword = "test"), repository)

        // verify that search was requested from data source
        verify(repository).search("test", 0)

        // verify that search request failed and data is empty
        withState(viewModel) {
            assertTrue(it.request is Fail)
            assertEquals(it.photos, emptyList<PhotoData>())
        }
    }


}