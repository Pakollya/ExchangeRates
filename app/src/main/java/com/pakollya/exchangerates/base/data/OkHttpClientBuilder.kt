package com.pakollya.exchangerates.base.data

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

interface OkHttpClientBuilder {
    fun httpClientBuilder(): OkHttpClient.Builder

    abstract class Abstract(
        private val interceptor: Interceptor,
        private val timeOutSeconds: Long = 60L
    ) : OkHttpClientBuilder {

        override fun httpClientBuilder() = OkHttpClient.Builder()
            .addInterceptor(interceptor.interceptor())
            .connectTimeout(timeOutSeconds, TimeUnit.SECONDS)
            .readTimeout(timeOutSeconds, TimeUnit.SECONDS)
    }

    class Base(
        interceptor: Interceptor,
    ) : Abstract(interceptor)
}