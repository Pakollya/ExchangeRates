package com.pakollya.exchangerates.favorites.presentation

import androidx.navigation.NavDirections
import com.pakollya.exchangerates.base.presentation.appComponent
import com.pakollya.exchangerates.base.presentation.injectViewModel
import com.pakollya.exchangerates.currencies.presentation.CurrenciesFragmentAbstract
import com.pakollya.exchangerates.databinding.CurrencyListLayoutBinding
import com.pakollya.exchangerates.main.presentation.MainActivity

class FavoritesFragment : CurrenciesFragmentAbstract<FavoritesViewModel>() {
    override val title: String = "Favorites"

    override val direction: NavDirections =
        FavoritesFragmentDirections.actionFavoritesFragmentToNamesFragment()

    override fun injectFragment(binding: CurrencyListLayoutBinding) {
        binding.root.context.appComponent.inject(this)
    }

    override fun provideViewModel() {
        viewModel = injectViewModel(viewModelFactory, activity as MainActivity)
    }
}