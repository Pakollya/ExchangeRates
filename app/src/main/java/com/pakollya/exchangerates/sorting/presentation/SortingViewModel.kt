package com.pakollya.exchangerates.sorting.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.presentation.BaseViewModel
import com.pakollya.exchangerates.base.presentation.Init
import com.pakollya.exchangerates.names.presentation.ItemsUi
import com.pakollya.exchangerates.sorting.domain.SortingInteractor
import javax.inject.Inject

class SortingViewModel @Inject constructor(
    private val sortingInteractor: SortingInteractor,
    private val communications: SortingCommunications,
    dispatchers: Dispatchers,
) : BaseViewModel(dispatchers), ObserveSorting, Init, ShowSorting {

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) showSorting()
    }

    override fun showSorting() {
        handle {
            sortingInteractor.sorting {
                communications.showSorting(it)
            }
        }
    }

    override fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communications.observeSorting(owner, observer)

    override fun observeSortingList(owner: LifecycleOwner, observer: Observer<ItemsUi>) =
        communications.observeSortingList(owner, observer)
}