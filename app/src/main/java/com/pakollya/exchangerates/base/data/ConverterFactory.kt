package com.pakollya.exchangerates.base.data

import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

interface ConverterFactory {

    fun converterFactory(): Converter.Factory

    class Base : ConverterFactory {

        override fun converterFactory(): Converter.Factory = GsonConverterFactory.create()
    }
}