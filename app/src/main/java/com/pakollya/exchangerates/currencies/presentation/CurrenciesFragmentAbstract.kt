package com.pakollya.exchangerates.currencies.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.pakollya.exchangerates.base.presentation.UiMapper
import com.pakollya.exchangerates.base.presentation.ViewBindingFragment
import com.pakollya.exchangerates.currencies.presentation.adapter.CurrenciesAdapter
import com.pakollya.exchangerates.databinding.CurrencyListLayoutBinding
import com.pakollya.exchangerates.main.presentation.ToolbarTitle
import com.pakollya.exchangerates.sorting.presentation.SortingBottomSheetFragment
import javax.inject.Inject

abstract class CurrenciesFragmentAbstract<VM : CurrenciesViewModelAbstract> :
    ViewBindingFragment<CurrencyListLayoutBinding>(CurrencyListLayoutBinding::inflate),
    InjectFragment<CurrencyListLayoutBinding>, ProvideViewModel, NavigateOnClick {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: VM

    protected open val direction: NavDirections =
        CurrenciesFragmentDirections.actionCurrenciesFragmentToNamesFragment()

    protected open val title: String = "Exchange Rates"

    private var toolbarTitle: ToolbarTitle = ToolbarTitle.Empty()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        toolbarTitle = context as ToolbarTitle
    }

    override fun onViewBindingCreated(
        binding: CurrencyListLayoutBinding,
        savedInstanceState: Bundle?,
    ) {
        super.onViewBindingCreated(binding, savedInstanceState)
        injectFragment(binding)
        provideViewModel()

        toolbarTitle.setTitle(title)

        val currenciesAdapter = CurrenciesAdapter.Currencies()
        binding.list.adapter = currenciesAdapter

        navigateOnClick(binding.baseButton, findNavController(), direction)

        val bottomSheetFragment = SortingBottomSheetFragment()
        binding.sortButton.setOnClickListener {
            bottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragment.tag
            )
        }

        val uiMapper = UiMapper(binding.baseButton)

        viewModel.observeList(this) { currenciesUi ->
            currenciesUi.map(currenciesAdapter)
            currenciesUi.mapBase(uiMapper)
        }

        viewModel.observeProgress(this) {
            binding.progress.visibility = it
        }

        viewModel.observeSorting(this) {
            viewModel.showCurrencies()
        }

        viewModel.observeError(this) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun navigateOnClick(
        view: View,
        navController: NavController,
        direction: NavDirections,
    ) {
        view.setOnClickListener {
            navController.navigate(direction)
        }
    }
}