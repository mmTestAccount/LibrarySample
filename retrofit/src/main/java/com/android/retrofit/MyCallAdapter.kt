package com.example.android.myapplication.myretrofithttp

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class MyCallAdapter<R> (private val responseType: Type): CallAdapter<R, Any> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Any = MyCall(call)
}