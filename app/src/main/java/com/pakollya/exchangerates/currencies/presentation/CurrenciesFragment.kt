package com.pakollya.exchangerates.currencies.presentation

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.base.presentation.*
import com.pakollya.exchangerates.currencies.presentation.adapter.CurrenciesAdapter
import com.pakollya.exchangerates.databinding.CurrencyListLayoutBinding
import com.pakollya.exchangerates.main.presentation.MainActivity
import com.pakollya.exchangerates.main.presentation.ToolbarTitle
import javax.inject.Inject

class CurrenciesFragment : ViewBindingFragment<CurrencyListLayoutBinding>(
    CurrencyListLayoutBinding::inflate
) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CurrenciesViewModel

    private var toolbarTitle: ToolbarTitle = ToolbarTitle.Empty()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        toolbarTitle = context as ToolbarTitle
    }

    override fun onViewBindingCreated(
        binding: CurrencyListLayoutBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(binding, savedInstanceState)
        binding.root.context.appComponent.inject(this)
        viewModel = injectViewModel(viewModelFactory, activity as MainActivity)

        toolbarTitle.setTitle(getString(R.string.app_name))

        val currenciesAdapter = CurrenciesAdapter.Currencies()
        binding.list.adapter = currenciesAdapter

        val uiMapper = UiMapper(binding.baseButton)

        DirectionClickListener.Base()
            .apply(
                binding.baseButton,
                findNavController(),
                CurrenciesFragmentDirections.actionCurrenciesFragmentToNamesFragment()
            )

        BottomSheetClickListener.Base().apply(binding.sortButton, requireActivity())

        viewModel.observe(this) { currenciesUi ->
            currenciesUi.map(currenciesAdapter)
            currenciesUi.mapBase(uiMapper)
        }

        viewModel.observeProgress(this) { visibility ->
            visibility.apply(binding.progress)
        }

        viewModel.observeBaseCurrency(this) {
            viewModel.updateCurrencies()
        }

        viewModel.observeSorting(this) {
            viewModel.updateCurrencies()
        }

        viewModel.observeError(this) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
}