package com.pakollya.exchangerates.names.data.cloud

import com.pakollya.exchangerates.base.data.MakeService
import com.pakollya.exchangerates.base.data.RetrofitBuilder
import com.pakollya.exchangerates.utils.BASE_URL

interface CurrencyNameApiService {

    fun currencyNamesService(): CurrencyNamesService

    class Base(
        retrofitBuilder: RetrofitBuilder,
    ) : MakeService.Abstract(
        retrofitBuilder
    ), CurrencyNameApiService {

        override fun baseUrl(): String = BASE_URL

        override fun currencyNamesService(): CurrencyNamesService =
            service(CurrencyNamesService::class.java)
    }
}