package com.pakollya.exchangerates.sorting.domain

import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.data.HandleError
import com.pakollya.exchangerates.base.domain.Interactor
import com.pakollya.exchangerates.names.presentation.ItemsUi

interface SortingInteractor {

    suspend fun sorting(
        successful: (ItemsUi) -> Unit
    )

    class Base(
        private val sortingDomainMapper: SortingDomain.Mapper<ItemsUi>,
        private val sortingRepository: SortingRepository,
        dispatchers: Dispatchers,
        handleError: HandleError
    ) : Interactor.Abstract(dispatchers, handleError), SortingInteractor {

        override suspend fun sorting(
            successful: (ItemsUi) -> Unit
        ) = handle(successful) {
            val data = sortingRepository.sorting()
            return@handle data.map(sortingDomainMapper)
        }
    }
}