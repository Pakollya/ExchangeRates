package com.pakollya.exchangerates.names.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.base.presentation.Visibility

interface ObserveNames {

    fun observeNames(owner: LifecycleOwner, observer: Observer<ItemsUi>)

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>)

    fun observeBaseCurrency(owner: LifecycleOwner, observer: Observer<String>)
}