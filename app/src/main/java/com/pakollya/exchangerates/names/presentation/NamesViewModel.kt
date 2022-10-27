package com.pakollya.exchangerates.names.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.presentation.BaseViewModel
import com.pakollya.exchangerates.base.presentation.Init
import com.pakollya.exchangerates.base.presentation.Visibility
import com.pakollya.exchangerates.names.domain.NamesInteractor
import javax.inject.Inject

class NamesViewModel @Inject constructor(
    private val namesInteractor: NamesInteractor,
    private val communication: NamesCommunications,
    dispatchers: Dispatchers
) : BaseViewModel(dispatchers), ObserveNames, Init, ShowNames {

    private val atFinish = {
        communication.showProgress(Visibility.Gone())
    }

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            communication.showProgress(Visibility.Visible())
            showNames()
    }

    override fun showNames() {
        handle {
            namesInteractor.names(atFinish) { communication.showNames(it) }
        }
    }

    override fun observeBaseCurrency(owner: LifecycleOwner, observer: Observer<String>) =
        communication.observeBaseCurrency(owner, observer)

    override fun observeNames(owner: LifecycleOwner, observer: Observer<ItemsUi>) =
        communication.observeNames(owner, observer)

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Visibility>) =
        communication.observeProgress(owner, observer)
}