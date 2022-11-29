package com.pakollya.exchangerates.names.presentation

import android.view.View.GONE
import android.view.View.VISIBLE
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
    dispatchers: Dispatchers,
) : BaseViewModel(dispatchers), ObserveNames, Init, ShowNames {

    override fun init(isFirstRun: Boolean) {
        communication.showNavigation(GONE)
        if (isFirstRun) {
            communication.showProgress(VISIBLE)
            showNames()
        }
    }

    override fun showNames() {
        handle {
            namesInteractor.names {
                communication.showNames(it)
                communication.showProgress(GONE)
            }
        }
    }

    override fun observeBaseCurrency(owner: LifecycleOwner, observer: Observer<String>) =
        communication.observeBaseCurrency(owner, observer)

    override fun observeNames(owner: LifecycleOwner, observer: Observer<ItemsUi>) =
        communication.observeNames(owner, observer)

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        communication.observeProgress(owner, observer)
}