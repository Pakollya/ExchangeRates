package com.pakollya.exchangerates.currencies.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.base.presentation.Communication
import com.pakollya.exchangerates.base.presentation.ErrorCommunication
import com.pakollya.exchangerates.base.presentation.ProgressCommunication
import com.pakollya.exchangerates.base.presentation.Visibility
import com.pakollya.exchangerates.favorites.presentation.FavoritesCommunication
import com.pakollya.exchangerates.names.data.BaseCurrencyCommunication
import com.pakollya.exchangerates.sorting.data.UpdateSorting

interface CurrenciesCommunications : ObserveCurrencies {

    fun showProgress(show: Visibility)

    fun showCurrencies(currencies: CurrenсiesUi)

    abstract class CurrenciesCommunicationsAbstract(
        private val progress: ProgressCommunication,
        private val sorting: UpdateSorting,
        private val error: ErrorCommunication,
        private val currenciesList: Communication.Mutable<CurrenсiesUi>,
    ) : CurrenciesCommunications {

        override fun observeList(owner: LifecycleOwner, observer: Observer<CurrenсiesUi>) =
            currenciesList.observe(owner, observer)

        override fun showProgress(show: Visibility) = progress.map(show)

        override fun showCurrencies(currencies: CurrenсiesUi) = currenciesList.map(currencies)

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Visibility>) =
            progress.observe(owner, observer)

        override fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>) =
            sorting.observe(owner, observer)

        override fun observeError(owner: LifecycleOwner, observer: Observer<String>) =
            error.observe(owner, observer)
    }

    class Base(
        progress: ProgressCommunication,
        sorting: UpdateSorting,
        error: ErrorCommunication,
        currenciesList: CurrenciesListCommunication,
    ) : CurrenciesCommunicationsAbstract(
        progress, sorting, error, currenciesList
    )

    class Favorite(
        progress: ProgressCommunication,
        sorting: UpdateSorting,
        error: ErrorCommunication,
        favoritesCommunication: FavoritesCommunication,
    ) : CurrenciesCommunicationsAbstract(
        progress, sorting, error, favoritesCommunication
    )
}

interface ObserveCurrencies {

    fun observeList(owner: LifecycleOwner, observer: Observer<CurrenсiesUi>)

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Visibility>)

    fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>)

    fun observeError(owner: LifecycleOwner, observer: Observer<String>)
}