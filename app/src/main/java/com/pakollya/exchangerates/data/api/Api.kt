package com.pakollya.exchangerates.data.api

import com.pakollya.exchangerates.utils.API_KEY_HEADER
import com.pakollya.exchangerates.utils.BASE
import com.pakollya.exchangerates.utils.REQUEST_LATEST
import com.pakollya.exchangerates.utils.REQUEST_SYMBOLS
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Api {
    @GET(REQUEST_LATEST)
    suspend fun getRateList(
        @Header(API_KEY_HEADER) apiKey: String
    ): String

    @GET(REQUEST_LATEST)
    suspend fun getRateList(
        @Header(API_KEY_HEADER) apiKey: String,
        @Query(BASE) base: String
    ): String

    @GET(REQUEST_SYMBOLS)
    suspend fun getSymbolList(
        @Header(API_KEY_HEADER) apiKey: String
    ): String
}