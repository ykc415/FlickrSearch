package com.example.flickrsearch.data.dto

data class FlickrSearchResponse(
    val photos: FlickrPhotoPage,
    val stat: String
)

data class FlickrPhotoPage(
    val page: Int,
    val perpage: Int,
    val photo: List<FlickrPhoto> = mutableListOf()
)

data class FlickrPhoto(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val url: String

)