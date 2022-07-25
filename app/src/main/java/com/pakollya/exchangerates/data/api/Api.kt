package com.pakollya.exchangerates.data.api

import com.pakollya.exchangerates.utils.REQUEST_LATEST
import com.pakollya.exchangerates.utils.REQUEST_SYMBOLS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Api {
    @GET(REQUEST_LATEST)
    fun getRateList(
        @Header("apikey") apiKey: String
    ): Response<RateApiResponse>

    @GET(REQUEST_LATEST)
    fun getRateList(
        @Header("apikey") apiKey: String,
        @Query("base") base: String
    ): Response<RateApiResponse>

    @GET(REQUEST_SYMBOLS)
    fun getSymbolList(
        @Header("apikey") apiKey: String
    ): Response<SymbolApiResponse>
}