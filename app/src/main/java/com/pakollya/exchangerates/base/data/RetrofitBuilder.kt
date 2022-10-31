package com.pakollya.exchangerates.base.data

import retrofit2.Retrofit

interface RetrofitBuilder {
    fun retrofitBuilder(): Retrofit.Builder

    abstract class Abstract(
        private val converterFactory: ConverterFactory,
        private val httpClientBuilder: OkHttpClientBuilder,
    ) : RetrofitBuilder {

        override fun retrofitBuilder(): Retrofit.Builder = Retrofit.Builder()
            .addConverterFactory(converterFactory.converterFactory())
            .client(httpClientBuilder.httpClientBuilder().build())
    }

    class Base(
        converterFactory: ConverterFactory,
        httpClientBuilder: OkHttpClientBuilder,
    ) : Abstract(
        converterFactory,
        httpClientBuilder
    )
}