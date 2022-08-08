package com.pakollya.exchangerates.presentation.favorite

import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.pakollya.exchangerates.presentation.MainActivity
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.databinding.FragmentCurrencyListBinding
import com.pakollya.exchangerates.presentation.base.BaseCurrencyListFragment
import com.pakollya.exchangerates.presentation.base.appComponent
import com.pakollya.exchangerates.presentation.list.CurrencyListViewModel

class FavoritesCurrencyListFragment: BaseCurrencyListFragment() {

    override lateinit var viewModel: CurrencyListViewModel

    override fun inject(binding: FragmentCurrencyListBinding) {
        binding.root.context.appComponent.inject(this)
    }

    override fun setToolbarTitle() {
        (activity as MainActivity).setToolbarTitle(getString(R.string.favorites))
    }

    override fun setFavoriteFragment() {
        viewModel.isFavoriteList = true

    }

    override fun onFavoriteClick(name: String?) {
        super.onFavoriteClick(name)
        showSnackbar(name)
    }

    override fun navigateToNameList() {
        val direction = FavoritesCurrencyListFragmentDirections.actionFavoritesCurrencyListFragmentToCurrencyNameListFragment()
        findNavController().navigate(direction)
    }

    private fun showSnackbar(name: String?) {
        viewBinding?.list?.let { recyclerView ->
            Snackbar.make(recyclerView, getString(R.string.favorite_was_deleted), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.undo)) {
                    viewModel.recoverFavorite(name)
                }.show()
        }
    }
}