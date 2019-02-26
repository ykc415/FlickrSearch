package com.example.flickrsearch.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


internal inline fun <T> LifecycleOwner.observe(liveData : LiveData<T>, crossinline block : (T) -> Unit) =
    liveData.observe(this, Observer {
        block(liveData.value!!)
    })

