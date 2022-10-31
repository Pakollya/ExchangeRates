package com.pakollya.exchangerates.currencies.presentation

import com.pakollya.exchangerates.base.presentation.*
import com.pakollya.exchangerates.databinding.CurrencyListLayoutBinding
import com.pakollya.exchangerates.main.presentation.MainActivity

class CurrenciesFragment : CurrenciesFragmentAbstract<CurrenciesViewModel>() {

    override fun injectFragment(binding: CurrencyListLayoutBinding) {
        binding.root.context.appComponent.inject(this)
    }

    override fun provideViewModel() {
        viewModel = injectViewModel(viewModelFactory)
    }
}