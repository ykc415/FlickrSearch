package com.example.flickrsearch.ui.main

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flickrsearch.R
import com.example.flickrsearch.data.Repository

typealias Keywords = List<String>

class MainViewModel(
    val repository: Repository,
    val resources: Resources

): ViewModel() {

    val keywords = MutableLiveData<Keywords>().apply { value = initKeywords() }
    val count = MutableLiveData<Int>().apply { value = 0 }


    fun add() {
        repository.search("", 0)
    }

    fun initKeywords(): Keywords {
        return resources.getStringArray(R.array.keywords).toList()
    }
}