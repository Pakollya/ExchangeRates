package com.pakollya.exchangerates.currencies.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.base.presentation.*
import com.pakollya.exchangerates.favorites.presentation.FavoritesCommunication
import com.pakollya.exchangerates.sorting.data.UpdateSorting

interface CurrenciesCommunications : ObserveCurrencies, ShowProgress, ShowNavigation {

    fun showCurrencies(currencies: CurrenсiesUi)

    abstract class CurrenciesCommunicationsAbstract(
        private val navigation: BottomNavigationCommunication,
        private val progress: ProgressCommunication,
        private val sorting: UpdateSorting,
        private val error: ErrorCommunication,
        private val currenciesList: Communication.Mutable<CurrenсiesUi>,
    ) : CurrenciesCommunications {

        override fun observeList(owner: LifecycleOwner, observer: Observer<CurrenсiesUi>) =
            currenciesList.observe(owner, observer)

        override fun showProgress(show: Int) = progress.map(show)

        override fun showNavigation(show: Int) = navigation.map(show)

        override fun showCurrencies(currencies: CurrenсiesUi) = currenciesList.map(currencies)

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
            progress.observe(owner, observer)

        override fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>) =
            sorting.observe(owner, observer)

        override fun observeError(owner: LifecycleOwner, observer: Observer<String>) =
            error.observe(owner, observer)
    }

    class Base(
        navigation: BottomNavigationCommunication,
        progress: ProgressCommunication,
        sorting: UpdateSorting,
        error: ErrorCommunication,
        currenciesList: CurrenciesListCommunication,
    ) : CurrenciesCommunicationsAbstract(
        navigation, progress, sorting, error, currenciesList
    )

    class Favorite(
        navigation: BottomNavigationCommunication,
        progress: ProgressCommunication,
        sorting: UpdateSorting,
        error: ErrorCommunication,
        favoritesCommunication: FavoritesCommunication,
    ) : CurrenciesCommunicationsAbstract(
        navigation, progress, sorting, error, favoritesCommunication
    )
}