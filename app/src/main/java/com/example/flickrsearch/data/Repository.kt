package com.example.flickrsearch.data

import com.example.flickrsearch.data.dto.FlickrSearchResponse
import io.reactivex.Single

interface Repository {

    fun search(keyword: String, page: Int): Single<FlickrSearchResponse>

}