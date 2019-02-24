package com.example.flickrsearch.data

import com.example.flickrsearch.data.dto.FlickrSearchResponse
import com.example.flickrsearch.data.remote.FlickrApi
import io.reactivex.Single

class RepositoryImpl(
    private val remoteApi: FlickrApi

) : Repository {

    override fun search(keyword: String, page: Int): Single<FlickrSearchResponse> {

        return remoteApi.search(
            keyword = keyword,
            page = page
        )
    }

}