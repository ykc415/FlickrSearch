package com.example.flickrsearch.data.remote

import com.example.flickrsearch.BuildConfig
import com.example.flickrsearch.data.dto.FlickrSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {

    @GET("rest")
    fun search(
        @Query("tags") keyword: String,
        @Query("page") page: Int,
        @Query("title") title: String = keyword,
        @Query("per_page") perPage: Int = 20,
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallBack: Int = 1

    ): Single<FlickrSearchResponse>
}