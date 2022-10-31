package com.pakollya.exchangerates.base.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pakollya.exchangerates.base.core.Dispatchers

abstract class BaseViewModel(
    private val dispatchers: Dispatchers,
) : ViewModel() {

    protected fun <T> handle(
        block: suspend () -> T,
    ) = dispatchers.launchBackground(viewModelScope) {
        block.invoke()
    }
}

abstract class ViewModelAbstract<T>(
    protected open val communication: Communication.Mutable<T>,
    dispatchers: Dispatchers,
) : BaseViewModel(dispatchers), Communication.Observe<T> {

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) =
        communication.observe(owner, observer)
}