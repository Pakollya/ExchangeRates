package com.pakollya.exchangerates.presentation.base

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pakollya.exchangerates.presentation.MainActivity
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.databinding.FragmentCurrencyListBinding
import com.pakollya.exchangerates.domain.CurrencyInteractor
import com.pakollya.exchangerates.presentation.adapter.CurrencyListAdapter
import com.pakollya.exchangerates.presentation.list.CurrencyListViewModel
import com.pakollya.exchangerates.presentation.view.SortedByBottomSheetFragment
import com.pakollya.exchangerates.utils.USD
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseCurrencyListFragment: ViewBindingFragment<FragmentCurrencyListBinding>(
    FragmentCurrencyListBinding::inflate
) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var interactor: CurrencyInteractor

    abstract var viewModel: CurrencyListViewModel

    private val adapter: CurrencyListAdapter by lazy { CurrencyListAdapter(
        interactor,
        favoriteClick = { name ->
            onFavoriteClick(name)
        }
    ) }

    override fun onViewBindingCreated(
        binding: FragmentCurrencyListBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(binding, savedInstanceState)
        inject(binding)
        viewModel = injectViewModel(viewModelFactory, activity as MainActivity)

        setFavoriteFragment()
        setToolbarTitle()

        binding.list.adapter = adapter

        binding.sortButton.setOnClickListener {
            val bottomSheetFragment = SortedByBottomSheetFragment()
            bottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragment.tag
            )
        }

        binding.baseButton.setOnClickListener {
            navigateToNameList()
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currencies.collect{ currencyList ->
                    if (currencyList != null) {
                        adapter.submitList(currencyList)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.baseName.collect{ name ->
                    binding.baseButton.text = getString(R.string.currency, name ?: USD)
                    viewModel.updateCurrencyListByName()
                }
            }
        }
    }

    open fun onFavoriteClick(name: String?) {
        viewModel.onFavoriteClick()
    }

    abstract fun navigateToNameList()

    abstract fun setToolbarTitle()

    abstract fun inject(binding: FragmentCurrencyListBinding)

    abstract fun setFavoriteFragment()
}