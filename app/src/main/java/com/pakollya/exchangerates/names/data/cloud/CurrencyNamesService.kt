package com.pakollya.exchangerates.names.data.cloud

import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud.CurrencyNames
import com.pakollya.exchangerates.utils.API_KEY_HEADER
import com.pakollya.exchangerates.utils.REQUEST_SYMBOLS
import retrofit2.http.GET
import retrofit2.http.Header

interface CurrencyNamesService {

    @GET(REQUEST_SYMBOLS)
    suspend fun names(
        @Header(API_KEY_HEADER) apiKey: String
    ): CurrencyNames
}