package com.example.android.myapplication.myretrofithttp

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder(baseUrl: String) {
    var retrofit: Retrofit? = null
    init {
        val okHttpClientInstance = UnsafeOkHttpClient()
        val okHttpClient = okHttpClientInstance.unSafeOkHttpClient()
        val httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = okHttpClient.addInterceptor(httpLogging)

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .addCallAdapterFactory(MyCallAdapterFactory.create())
            .client(httpClientBuilder.build())
            .build()
    }

    fun<T> buildService(service: Class<T>): T {
        return retrofit!!.create(service)
    }
}