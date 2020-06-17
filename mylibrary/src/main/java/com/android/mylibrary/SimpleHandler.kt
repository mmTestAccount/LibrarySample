package com.android.mylibrary

interface SimpleHandler<T> {
    fun accept(response: T, throwable: Throwable?)
}