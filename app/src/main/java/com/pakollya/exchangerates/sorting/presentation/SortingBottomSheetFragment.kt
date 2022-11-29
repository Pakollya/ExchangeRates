package com.pakollya.exchangerates.sorting.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pakollya.exchangerates.base.presentation.appComponent
import com.pakollya.exchangerates.base.presentation.injectViewModel
import com.pakollya.exchangerates.sorting.presentation.adapter.SortingAdapter
import com.pakollya.exchangerates.databinding.BottomSheetSortingLayoutBinding
import com.pakollya.exchangerates.main.presentation.MainActivity
import javax.inject.Inject

class SortingBottomSheetFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SortingViewModel
    private lateinit var binding: BottomSheetSortingLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetSortingLayoutBinding.inflate(inflater, container, false)
        binding.root.context.appComponent.inject(this)
        viewModel = injectViewModel(viewModelFactory)

        val adapter = SortingAdapter.Sorting()
        binding.sortingList.adapter = adapter

        viewModel.init(savedInstanceState == null)

        viewModel.observeSortingList(this) { sortingUi ->
            sortingUi.map(adapter)
        }

        viewModel.observeSorting(this) {
            viewModel.showSorting()
        }

        return binding.root
    }
}