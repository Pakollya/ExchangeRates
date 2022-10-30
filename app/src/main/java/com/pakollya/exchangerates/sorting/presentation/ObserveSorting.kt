package com.pakollya.exchangerates.sorting.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.names.presentation.ItemsUi

interface ObserveSorting {

    fun observeSortingList(owner: LifecycleOwner, observer: Observer<ItemsUi>)

    fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>)
}