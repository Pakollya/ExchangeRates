package com.pakollya.exchangerates.base.domain

import com.pakollya.exchangerates.base.data.HandleError
import java.net.UnknownHostException

class HandleDomainError : HandleError {

    override fun handle(error: Exception) =
        if (error is UnknownHostException)
            NoInternetConnectionException()
        else
            ServiceUnavailableException()
}