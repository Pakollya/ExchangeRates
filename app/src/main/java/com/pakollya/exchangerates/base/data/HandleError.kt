package com.pakollya.exchangerates.base.data

interface HandleError {

    fun handle(error: Exception): Exception
}