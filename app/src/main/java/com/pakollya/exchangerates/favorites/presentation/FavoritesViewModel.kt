package com.pakollya.exchangerates.favorites.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.presentation.BaseViewModel
import com.pakollya.exchangerates.base.presentation.ErrorCommunication
import com.pakollya.exchangerates.base.presentation.ProgressCommunication
import com.pakollya.exchangerates.base.presentation.Visibility
import com.pakollya.exchangerates.currencies.presentation.CurrenсiesUi
import com.pakollya.exchangerates.favorites.domain.FavoritesInteractor
import com.pakollya.exchangerates.names.data.UpdateBaseCurrency
import com.pakollya.exchangerates.sorting.data.UpdateSorting
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val favoritesInteractor: FavoritesInteractor,
    private val progressCommunication: ProgressCommunication.Mutable,
    private val updateBaseCurrency: UpdateBaseCurrency.Mutable,
    private val updateSorting: UpdateSorting.Mutable,
    private val errorCommunication: ErrorCommunication.Mutable,
    favoritesCommunication: FavoritesCommunication,
    dispatchers: Dispatchers
) : BaseViewModel<CurrenсiesUi>(favoritesCommunication, dispatchers) {

    private val atFinish = {
        progressCommunication.map(Visibility.Gone())
    }

    init {
        updateFavorites()
    }

    fun updateFavorites() {
        progressCommunication.map(Visibility.Visible())
        handle {
            favoritesInteractor.favorites(atFinish) { communication.map(it) }
        }
    }

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Visibility>) {
        progressCommunication.observe(owner, observer)
    }

    fun observeBaseCurrency(owner: LifecycleOwner, observer: Observer<String>) {
        updateBaseCurrency.observe(owner, observer)
    }

    fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>) {
        updateSorting.observe(owner, observer)
    }

    fun observeError(owner: LifecycleOwner, observer: Observer<String>) {
        errorCommunication.observe(owner, observer)
    }
}