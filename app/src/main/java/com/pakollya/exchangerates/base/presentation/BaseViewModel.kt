package com.pakollya.exchangerates.base.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pakollya.exchangerates.base.core.Dispatchers

abstract class BaseViewModel<T>(
    protected open val communication: Communication.Mutable<T>,
    private val dispatchers: Dispatchers
) : ViewModel(), Communication.Observe<T> {

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) =
        communication.observe(owner, observer)

    protected fun <T> handle(
        block: suspend () -> T
    ) = dispatchers.launchBackground(viewModelScope) {
        block.invoke()
    }
}