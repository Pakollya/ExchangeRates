package com.pakollya.exchangerates.base.data

interface MakeService {

    fun <T> service(item: Class<T>): T

    abstract class Abstract(
        private val retrofitBuilder: RetrofitBuilder,
    ) : MakeService {

        private val retrofit by lazy {
            retrofitBuilder.retrofitBuilder()
                .baseUrl(baseUrl())
                .build()
        }

        override fun <T> service(item: Class<T>): T = retrofit.create(item)

        protected open fun baseUrl(): String = "https://www.google.com"
    }
}