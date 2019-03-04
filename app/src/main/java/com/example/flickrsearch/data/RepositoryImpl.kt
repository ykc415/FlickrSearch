package com.example.flickrsearch.data

import com.example.flickrsearch.data.dto.FlickrSearchResponse
import com.example.flickrsearch.data.remote.FlickrApiService
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RepositoryImpl(
    private val remoteApiService: FlickrApiService

) : Repository {

    override fun search(keyword: String, page: Int): Single<FlickrSearchResponse> {

        return remoteApiService.search(keyword = keyword, page = page)
            .subscribeOn(Schedulers.io())

    }
}