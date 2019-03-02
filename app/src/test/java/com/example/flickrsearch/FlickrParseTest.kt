package com.example.flickrsearch

import com.example.flickrsearch.data.dto.FlickrPhoto
import com.example.flickrsearch.utils.FlickrUrlParser
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before


internal class FlickrParseTest {

    lateinit var flickrPhoto: FlickrPhoto

    @Before
    fun setup() {
        flickrPhoto = FlickrPhoto(
            id = "id",
            owner = "owner",
            secret = "secret",
            server = "server",
            farm = 0,
            title = "title",
            url = ""
        )
    }

    @Test
    fun parse_isCorrect() {
        assertEquals("https://farm0.staticflickr.com/server/id_secret.jpg", FlickrUrlParser.parse(flickrPhoto))
    }
}