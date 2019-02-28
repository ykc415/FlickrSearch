package com.example.flickrsearch.utils

import com.example.flickrsearch.data.dto.FlickrPhoto


object FlickrUrlParser {

    fun parse(flickrPhoto: FlickrPhoto): String {
        return "https://farm${flickrPhoto.farm}.staticflickr.com/${flickrPhoto.server}/${flickrPhoto.id}_${flickrPhoto.secret}.jpg"
    }

}