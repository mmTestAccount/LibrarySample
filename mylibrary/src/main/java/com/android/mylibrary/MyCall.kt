package com.android.mylibrary

import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MyCall<R>(private val call: Call<R>) {
    // support for java
    fun run(responseHandler: SimpleHandler<R?>) = run(responseHandler::accept)
    fun process(responseHandler: SimpleHandler<R?>) = process(responseHandler::accept)

    @Throws(IOException::class)
    fun run() = call.execute()

    fun run(responseHandler: (R?, Throwable?) -> Unit) {
        // run in the same thread
        try {
            // call and handle response
            val response = call.execute()
            handleResponse(response, responseHandler)

        } catch (t: IOException) {
            responseHandler(null, t)
        }
    }

    fun process(responseHandler: (R?, Throwable?) -> Unit): Subscription {
        val subscription = Subscription()
        // define callback
        val callback = object : Callback<R> {

            override fun onFailure(call: Call<R>?, t: Throwable?) =
                responseHandler(null, t)

            override fun onResponse(call: Call<R>?, r: Response<R>?) =
                handleResponse(r, responseHandler)
        }

        // enqueue network call
        call.enqueue(callback)

        return subscription
    }

    private fun handleResponse(response: Response<R>?, handler: (R?, Throwable?) -> Unit) {
        if (response?.isSuccessful == true) {
            handler(response.body(), null)
        } else {
            if (response?.code() in 400..511)
                handler(null, HttpException(response!!))

            else handler(response?.body(), null)

        }
    }
}