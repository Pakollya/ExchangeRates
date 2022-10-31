package com.pakollya.exchangerates.base.data

import okhttp3.logging.HttpLoggingInterceptor

interface Interceptor {

    fun interceptor(): HttpLoggingInterceptor

    abstract class Abstract(
        private val loggingLevel: HttpLoggingInterceptor.Level
    ) : Interceptor {

        override fun interceptor() = HttpLoggingInterceptor().apply {
            level = loggingLevel
        }
    }

    class Debug : Abstract(HttpLoggingInterceptor.Level.BODY)

    class Release : Abstract(HttpLoggingInterceptor.Level.NONE)
}