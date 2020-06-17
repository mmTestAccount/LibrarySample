package com.example.android.myapplication.myretrofithttp

interface SimpleHandler<T> {
    fun accept(response: T, throwable: Throwable?)
}