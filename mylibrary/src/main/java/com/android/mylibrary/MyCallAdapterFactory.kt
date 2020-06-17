package com.android.mylibrary

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class MyCallAdapterFactory private constructor () : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = returnType.let {
        return try {
            // get enclosing type
            val enclosingType = (it as ParameterizedType)

            // ensure enclosing type is 'Simple'
            if (enclosingType.rawType != MyCall::class.java)
                null
            else {
                val type = enclosingType.actualTypeArguments[0]
                MyCallAdapter<Any>(type)
            }
        } catch (ex: ClassCastException) {
            null
        }
    }

    companion object {
        @JvmStatic
        fun create() = MyCallAdapterFactory()
    }
}