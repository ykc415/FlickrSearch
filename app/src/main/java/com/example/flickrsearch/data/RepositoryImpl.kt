package com.example.flickrsearch.data

import com.example.flickrsearch.data.dto.FlickrSearchResponse
import com.example.flickrsearch.data.remote.FlickrApiService
import io.reactivex.Single

class RepositoryImpl(
    private val remoteApiService: FlickrApiService

) : Repository {

    override fun search(keyword: String, page: Int): Single<FlickrSearchResponse> {

        return remoteApiService.search(
            keyword = keyword,
            page = page
        )
    }

}