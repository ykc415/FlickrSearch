package com.example.flickrsearch.ui.main

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.example.flickrsearch.R
import com.example.flickrsearch.data.Repository
import com.example.flickrsearch.data.dto.FlickrSearchResponse
import com.example.flickrsearch.ui.base.BaseViewModel
import com.example.flickrsearch.ui.main.model.PhotoData
import com.example.flickrsearch.utils.FlickrUrlParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

typealias Keywords = List<String>

class MainViewModel(
    val repository: Repository,
    val resources: Resources

): BaseViewModel() {

    companion object {

        const val FIRST_PAGE = 0
    }

    val TAG = this::class.java.simpleName

    val titleText    = MutableLiveData<String>().apply { value = resources.getStringArray(R.array.keywords).first() }
    val keywords     = MutableLiveData<Keywords>().apply { value = initKeywords() }
    val photos       = MutableLiveData<List<PhotoData>>()

    fun initKeywords(): Keywords {
        return resources.getStringArray(R.array.keywords).toList()
    }

    fun keywordButtonClicked(str: String) {
        titleText.value = str

        repository.search(str, FIRST_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ response ->
                photos.value = response.photos.photo.map { rawData ->
                    PhotoData(
                        title = rawData.title,
                        url   = FlickrUrlParser.parse(rawData)
                    )
                }
            }, { error ->
                error.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

}