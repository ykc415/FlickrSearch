package com.example.flickrsearch

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.example.flickrsearch.data.Repository
import com.example.flickrsearch.data.dto.FlickrPhoto
import com.example.flickrsearch.data.dto.FlickrPhotoPage
import com.example.flickrsearch.data.dto.FlickrSearchResponse
import com.example.flickrsearch.ui.main.MainState
import com.example.flickrsearch.ui.main.MainViewModel
import com.example.flickrsearch.ui.main.PhotoData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.subjects.SingleSubject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.ClassRule
import org.junit.Test
import java.net.SocketTimeoutException


class MainViewModelTest {

    companion object {

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()

        private val mockResponse: FlickrSearchResponse = FlickrSearchResponse(
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

        private val mockNewResponse = FlickrSearchResponse(
            photos = FlickrPhotoPage(
                page = 1,
                photo = listOf(
                    FlickrPhoto(
                        id = "id2",
                        owner = "owner2",
                        secret = "secret2",
                        server = "server2",
                        farm = -1,
                        title = "title2",
                        url = "url2"
                    )
                ),
                perpage = 20
            ),
            stat = "ok"
        )
    }

    private var repository: Repository = mock()

    private lateinit var viewModel: MainViewModel


    @Test
    fun fetchNextPage_init_success() {

        // use subject to be able to verify the loading state before any emissions
        val photosSubject = SingleSubject.create<FlickrSearchResponse>()
        whenever(repository.search("test", 0)).thenReturn(photosSubject)

        // given the viewmodel with default state
        viewModel = MainViewModel(MainState(currentKeyword = "test"), repository)

        viewModel.fetchNextPage()

        // verify that tasks were requested from the repository
        verify(repository).search("test", 0)

        withState(viewModel) { assertTrue(it.request is Loading) }

        // new emission from the data source happened
        photosSubject.onSuccess(mockResponse)

        // verify that tasks request was successful and the photo list is present
        withState(viewModel) {
            assertTrue(it.request is Success)
            assertTrue(it.photos.size == 1)
        }

        // verify that loading state has changed to false after the stream is completed

    }

    @Test
    fun fetchNextPage_failed() {

        val errorSubject = SingleSubject.create<FlickrSearchResponse>()
        whenever(repository.search("test", 0)).thenReturn(errorSubject)

        // given the viewmodel with default state
        viewModel = MainViewModel(MainState(currentKeyword = "test"), repository)

        viewModel.fetchNextPage()

        // verify that search was requested from data source
        verify(repository).search("test", 0)

        errorSubject.onError(SocketTimeoutException("Socket Time Out Exception"))

        // verify that search request failed and data is empty
        withState(viewModel) {
            assertTrue(it.request is Fail)
            assertEquals(it.photos, emptyList<PhotoData>())
        }
    }


    /**
     *  다음 페이지 로드 테스트
     * **/
    @Test
    fun fetchNextPage_loadNextPage() {

        val photoSubject = SingleSubject.create<FlickrSearchResponse>()
        whenever(repository.search("test", 1)).thenReturn(photoSubject)

        viewModel = MainViewModel(
            MainState(currentKeyword = "test",
                      photos = listOf(PhotoData("test_title", "test_url"))),
            repository
        )

        viewModel.fetchNextPage()

        withState(viewModel) { assertTrue(it.request is Loading) }

        photoSubject.onSuccess(mockResponse)

        withState(viewModel) {
            assertTrue(it.request is Success)
            assertTrue(it.photos.size == 2)
        }
    }


    //TODO 테스트코드 고쳐야됨
    /**
     *  키워드 클릭시 데이터 로드 테스트코드
     */
    @Test
    fun updateCurrentKeyword() {

        val photoSubject = SingleSubject.create<FlickrSearchResponse>()
        whenever(repository.search("test", 1)).thenReturn(photoSubject)

        viewModel = MainViewModel(
            MainState(currentKeyword = "current",
                photos = listOf(
                    PhotoData(title = "title", url = "url")
                ),
                request = Success(mockResponse)
            ),
            repository
        )

        viewModel.updateCurrentKeyword("nextKeyword")

        withState(viewModel) {
            assertEquals(it.currentKeyword, "nextKeyword")
        }

        viewModel.fetchNextPage()

        withState(viewModel) {
            assertEquals(it.currentKeyword, "nextKeyword")
            assertTrue(it.request is Loading)
        }

        photoSubject.onSuccess(mockNewResponse)

        withState(viewModel) {
            assertTrue(it.photos.size == 1)

            assertEquals(it.request(), mockNewResponse)
        }
    }

}