package com.pakollya.exchangerates.names.domain

import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.data.HandleError
import com.pakollya.exchangerates.base.domain.Interactor
import com.pakollya.exchangerates.names.presentation.ItemsUi

interface NamesInteractor {

    suspend fun names(
        atFinish: () -> Unit,
        successful: (ItemsUi) -> Unit
    )

    class Base(
        private val mapper: CurrencyNameDomain.Mapper<ItemsUi>,
        private val repository: CurrencyNamesRepository,
        dispatchers: Dispatchers,
        handleError: HandleError
    ) : Interactor.Abstract(dispatchers, handleError), NamesInteractor {

        override suspend fun names(
            atFinish: () -> Unit,
            successful: (ItemsUi) -> Unit
        ) = handle(successful, atFinish) {
            val data = repository.names()
            return@handle data.map(mapper)
        }
    }
}