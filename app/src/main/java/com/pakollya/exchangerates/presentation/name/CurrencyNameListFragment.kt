package com.pakollya.exchangerates.presentation.name

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.pakollya.exchangerates.presentation.MainActivity
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.databinding.FragmentCurrencyNameListBinding
import com.pakollya.exchangerates.domain.CurrencyInteractor
import com.pakollya.exchangerates.presentation.adapter.CurrencyNameListAdapter
import com.pakollya.exchangerates.presentation.base.ViewBindingFragment
import com.pakollya.exchangerates.presentation.base.appComponent
import com.pakollya.exchangerates.presentation.base.injectViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrencyNameListFragment : ViewBindingFragment<FragmentCurrencyNameListBinding>(
    FragmentCurrencyNameListBinding::inflate
) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var interactor: CurrencyInteractor

    private lateinit var viewModel: CurrencyNameListViewModel

    private val currencyNameListAdapter: CurrencyNameListAdapter by lazy { CurrencyNameListAdapter(
        interactor = interactor,
        clickItem = { name ->
            viewModel.saveBaseName(name)
            findNavController().popBackStack()
        }
    ) }

    override fun onViewBindingCreated(
        binding: FragmentCurrencyNameListBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(binding, savedInstanceState)
        binding.root.context.appComponent.inject(this)
        binding.list.adapter = currencyNameListAdapter
        viewModel = injectViewModel(viewModelFactory, activity as MainActivity)

        (activity as MainActivity).setToolbarTitle(getString(R.string.currencies))
        (activity as MainActivity).setToolbarNavigation()

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.names.collect{ nameList ->
                    nameList?.let { currencyNameListAdapter.submitList(it) }
                }
            }
        }
    }
}