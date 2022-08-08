package com.pakollya.exchangerates.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isInvisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pakollya.exchangerates.presentation.MainActivity
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.databinding.BottomSheetSortedByBinding
import com.pakollya.exchangerates.presentation.list.CurrencyListViewModel
import com.pakollya.exchangerates.presentation.list.SortedByType
import com.pakollya.exchangerates.presentation.base.appComponent
import com.pakollya.exchangerates.presentation.base.injectViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SortedByBottomSheetFragment : BottomSheetDialogFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: CurrencyListViewModel
    private lateinit var binding: BottomSheetSortedByBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetSortedByBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.root.context.appComponent.inject(this)
        viewModel = injectViewModel(viewModelFactory, activity as MainActivity)
        selectSortedByType(viewModel.sortedByType.value)

        binding.byNameLayout.setOnClickListener {
            viewModel.onSortClick(SortedByType.SORTED_BY_NAME)
        }
        binding.byNameDescLayout.setOnClickListener {
            viewModel.onSortClick(SortedByType.SORTED_BY_NAME_DESC)
        }
        binding.byValueLayout.setOnClickListener {
            viewModel.onSortClick(SortedByType.SORTED_BY_VALUE)
        }
        binding.byValueDescLayout.setOnClickListener {
            viewModel.onSortClick(SortedByType.SORTED_BY_VALUE_DESC)
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sortedByType.collect{
                    selectSortedByType(it)
                }
            }
        }

        return binding.root
    }

    private fun selectSortedByType(sortedByType: SortedByType?) {
        with(binding) {
            byNameLayout.setBackgroundColor(getColor(binding.root.context,
                getColorSelected(sortedByType == SortedByType.SORTED_BY_NAME)
            ))
            byNameImage.isInvisible = sortedByType != SortedByType.SORTED_BY_NAME

            byNameDescLayout.setBackgroundColor(getColor(binding.root.context,
                getColorSelected(sortedByType == SortedByType.SORTED_BY_NAME_DESC)
            ))
            byNameDescImage.isInvisible = sortedByType != SortedByType.SORTED_BY_NAME_DESC

            byValueLayout.setBackgroundColor(getColor(binding.root.context,
                getColorSelected(sortedByType == SortedByType.SORTED_BY_VALUE)
            ))
            byValueImage.isInvisible = sortedByType != SortedByType.SORTED_BY_VALUE

            byValueDescLayout.setBackgroundColor(getColor(binding.root.context,
                getColorSelected(sortedByType == SortedByType.SORTED_BY_VALUE_DESC)
            ))
            byValueDescImage.isInvisible = sortedByType != SortedByType.SORTED_BY_VALUE_DESC
        }
    }

    private fun getColorSelected(isSelected: Boolean): Int {
        return if (isSelected) R.color.backgroundBottomSheet else R.color.white
    }
}