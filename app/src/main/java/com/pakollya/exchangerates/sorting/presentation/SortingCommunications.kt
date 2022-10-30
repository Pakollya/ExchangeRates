package com.pakollya.exchangerates.sorting.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.names.presentation.ItemsUi
import com.pakollya.exchangerates.sorting.data.UpdateSorting

interface SortingCommunications : ObserveSorting {

    fun showSorting(sorting: ItemsUi)

    class Base(
        private val updateSorting: UpdateSorting,
        private val listCommunication: SortingListCommunication
    ) : SortingCommunications {

        override fun showSorting(sorting: ItemsUi) = listCommunication.map(sorting)

        override fun observeSortingList(owner: LifecycleOwner, observer: Observer<ItemsUi>) =
            listCommunication.observe(owner, observer)

        override fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>) =
            updateSorting.observe(owner, observer)
    }

}