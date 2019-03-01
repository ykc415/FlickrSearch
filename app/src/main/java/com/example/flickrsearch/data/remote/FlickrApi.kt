package com.example.flickrsearch.data.remote

import com.example.flickrsearch.BuildConfig
import com.example.flickrsearch.data.dto.FlickrSearchResponse
import com.example.flickrsearch.ui.main.PHOTO_COUNT
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


const val SEARCH_METHOD = "flickr.photos.search"
const val JSON = "json"

interface FlickrApiService {

    @GET("rest")
    fun search(
        @Query("tags") keyword: String,
        @Query("page") page: Int,
        @Query("title") title: String = keyword,
        @Query("per_page") perPage: Int = PHOTO_COUNT,
        @Query("method") method: String = SEARCH_METHOD,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = JSON,
        @Query("nojsoncallback") noJsonCallBack: Int = 1

    ): Single<FlickrSearchResponse>
}