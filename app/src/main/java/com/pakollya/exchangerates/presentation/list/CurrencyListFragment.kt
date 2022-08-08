package com.pakollya.exchangerates.presentation.list

import androidx.navigation.fragment.findNavController
import com.pakollya.exchangerates.presentation.MainActivity
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.databinding.FragmentCurrencyListBinding
import com.pakollya.exchangerates.presentation.base.BaseCurrencyListFragment
import com.pakollya.exchangerates.presentation.base.appComponent

class CurrencyListFragment : BaseCurrencyListFragment() {

    override lateinit var viewModel: CurrencyListViewModel

    override fun inject(binding: FragmentCurrencyListBinding) {
        binding.root.context.appComponent.inject(this)
    }

    override fun setToolbarTitle() {
        (activity as MainActivity).setToolbarTitle(getString(R.string.app_name))
    }


    override fun setFavoriteFragment() {
        viewModel.isFavoriteList = false
    }

    override fun navigateToNameList() {
        val direction = CurrencyListFragmentDirections.actionCurrencyListFragmentToCurrencyNameListFragment()
        findNavController().navigate(direction)
    }
}