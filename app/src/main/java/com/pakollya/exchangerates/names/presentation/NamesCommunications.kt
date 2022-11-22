package com.pakollya.exchangerates.names.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.base.presentation.BottomNavigationCommunication
import com.pakollya.exchangerates.base.presentation.ProgressCommunication
import com.pakollya.exchangerates.base.presentation.Visibility
import com.pakollya.exchangerates.currencies.presentation.ShowNavigation
import com.pakollya.exchangerates.currencies.presentation.ShowProgress
import com.pakollya.exchangerates.names.data.BaseCurrencyCommunication

interface NamesCommunications : ObserveNames, ShowProgress, ShowNavigation {

    fun showNames(names: ItemsUi)

    class Base(
        private val navigation: BottomNavigationCommunication,
        private val progress: ProgressCommunication,
        private val baseCurrency: BaseCurrencyCommunication,
        private val namesList: NamesListCommunication
    ) : NamesCommunications {

        override fun showProgress(show: Int) = progress.map(show)

        override fun showNames(names: ItemsUi) = namesList.map(names)

        override fun showNavigation(show: Int) = navigation.map(show)

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
            progress.observe(owner, observer)

        override fun observeNames(owner: LifecycleOwner, observer: Observer<ItemsUi>) =
            namesList.observe(owner, observer)

        override fun observeBaseCurrency(owner: LifecycleOwner, observer: Observer<String>) =
            baseCurrency.observe(owner, observer)
    }
}