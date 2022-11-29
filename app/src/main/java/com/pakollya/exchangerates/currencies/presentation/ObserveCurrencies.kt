package com.pakollya.exchangerates.currencies.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.base.presentation.Visibility

interface ObserveCurrencies {

    fun observeList(owner: LifecycleOwner, observer: Observer<CurrenсiesUi>)

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>)

    fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>)

    fun observeError(owner: LifecycleOwner, observer: Observer<String>)
}