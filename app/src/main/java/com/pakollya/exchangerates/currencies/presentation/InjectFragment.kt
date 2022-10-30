package com.pakollya.exchangerates.currencies.presentation

import androidx.viewbinding.ViewBinding

interface InjectFragment<VB : ViewBinding> {
    fun injectFragment(binding: VB)
}