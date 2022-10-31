package com.pakollya.exchangerates.names.data.cloud

import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud.CurrencyNames
import retrofit2.http.GET
import retrofit2.http.Header

interface CurrencyNamesService {

    @GET(REQUEST_SYMBOLS)
    suspend fun names(
        @Header(API_KEY_HEADER) apiKey: String
    ): CurrencyNames

    companion object {
        private const val REQUEST_SYMBOLS = "symbols"
        private const val API_KEY_HEADER = "apikey"
    }
}