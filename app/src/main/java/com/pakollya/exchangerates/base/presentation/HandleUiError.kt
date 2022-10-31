package com.pakollya.exchangerates.base.presentation

import androidx.annotation.StringRes
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.base.core.ResourcesManager
import com.pakollya.exchangerates.base.data.HandleError
import com.pakollya.exchangerates.base.domain.NoInternetConnectionException
import com.pakollya.exchangerates.base.domain.ServiceUnavailableException

abstract class HandleUiErrorAbstract(
    private val resourcesManager: ResourcesManager,
    private val errorCommunication: ErrorCommunication,
    private val handleError: HandleError = BaseHandleUiError(resourcesManager, errorCommunication)
) : HandleError {

    @StringRes
    protected open val noConnectionMessage: Int = R.string.no_connection_message

    @StringRes
    protected open val serviceUnavailableMessage: Int = R.string.no_service_message

    override fun handle(error: Exception): Exception {
        val messageId = when (error) {
            is NoInternetConnectionException -> noConnectionMessage
            is ServiceUnavailableException -> serviceUnavailableMessage
            else -> 0
        }
        return if (messageId == 0)
            handleError.handle(error)
        else {
            errorCommunication.map(resourcesManager.string(messageId))
            error
        }
    }
}

class BaseHandleUiError(
    private val resourcesManager: ResourcesManager,
    private val errorCommunication: ErrorCommunication
) : HandleError {

    override fun handle(error: Exception): Exception {
        errorCommunication.map(resourcesManager.string(R.string.unexpected_error_message))
        return error
    }
}

class HandleUiError(
    private val resourcesManager: ResourcesManager,
    private val errorCommunication: ErrorCommunication
) : HandleUiErrorAbstract(resourcesManager, errorCommunication)