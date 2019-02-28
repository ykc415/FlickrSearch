package com.example.flickrsearch.di

import com.example.flickrsearch.BuildConfig
import com.example.flickrsearch.BuildConfig.BASE_URL
import com.example.flickrsearch.data.Repository
import com.example.flickrsearch.data.RepositoryImpl
import com.example.flickrsearch.data.remote.FlickrApi
import com.example.flickrsearch.ui.main.MainViewModel
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

val appModule = module {

    single { createWebService<FlickrApi>(BASE_URL) }

    viewModel { MainViewModel(get(), androidContext().applicationContext.resources) }

    single { RepositoryImpl(get()) as Repository}

}

const val TIMEOUT = 20L

fun createLoggingInterceptor(): HttpLoggingInterceptor {

    return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
        if (message.startsWith("{") || message.startsWith("[")) {
            val prettyMsg = GsonBuilder().setPrettyPrinting()
                .create().toJson(JsonParser().parse(message))

            Timber.tag("OKHTTP").d(prettyMsg)
        } else
            Timber.tag("OKHTTP").d(message)
        })
        .apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

}

fun createOkHttpClient(): OkHttpClient {

    val okHttpClientBuilder = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)

    if(BuildConfig.DEBUG) {
        okHttpClientBuilder.addNetworkInterceptor(createLoggingInterceptor())
        okHttpClientBuilder.addNetworkInterceptor(StethoInterceptor())
    }

    return okHttpClientBuilder.build()
}

inline fun <reified T> createWebService(url: String): T {

    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

    return retrofit.create(T::class.java)
}


