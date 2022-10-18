package com.pakollya.exchangerates.base.presentation

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.pakollya.exchangerates.sorting.presentation.SortingBottomSheetFragment

interface BottomSheetClickListener {

    fun apply(view: View, requireActivity: FragmentActivity)

    class Base : BottomSheetClickListener {

        override fun apply(view: View, requireActivity: FragmentActivity) {
            view.setOnClickListener {
                val bottomSheetFragment = SortingBottomSheetFragment()
                bottomSheetFragment.show(
                    requireActivity.supportFragmentManager,
                    bottomSheetFragment.tag
                )
            }
        }
    }
}