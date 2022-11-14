package com.pakollya.exchangerates.names.presentation

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.base.presentation.ViewBindingFragment
import com.pakollya.exchangerates.base.presentation.appComponent
import com.pakollya.exchangerates.base.presentation.injectViewModel
import com.pakollya.exchangerates.names.presentation.adapter.CurrencyNameAdapter
import com.pakollya.exchangerates.databinding.CurrencyNameListLayoutBinding
import com.pakollya.exchangerates.main.presentation.MainActivity
import com.pakollya.exchangerates.main.presentation.ToolbarNavigation
import com.pakollya.exchangerates.main.presentation.ToolbarTitle
import javax.inject.Inject

class NamesFragment : ViewBindingFragment<CurrencyNameListLayoutBinding>(
    CurrencyNameListLayoutBinding::inflate
) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: NamesViewModel

    private var toolbarTitle: ToolbarTitle = ToolbarTitle.Empty()
    private var toolbarNavigation: ToolbarNavigation = ToolbarNavigation.Empty()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        toolbarTitle = context as ToolbarTitle
        toolbarNavigation = context as ToolbarNavigation
    }

    override fun onViewBindingCreated(
        binding: CurrencyNameListLayoutBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(binding, savedInstanceState)
        binding.root.context.appComponent.inject(this)
        viewModel = injectViewModel(viewModelFactory)

        toolbarTitle.setTitle(getString(R.string.currencies))
        toolbarNavigation.setDirection()

        val namesAdapter = CurrencyNameAdapter.Names()
        binding.list.adapter = namesAdapter

        viewModel.observeNames(this) { currenciesUi ->
            currenciesUi?.map(namesAdapter)
        }

        viewModel.observeBaseCurrency(this) {
            viewModel.showNames()
        }

        viewModel.observeProgress(this) {
            binding.progress.visibility = it
        }

        viewModel.init(savedInstanceState == null)
    }
}