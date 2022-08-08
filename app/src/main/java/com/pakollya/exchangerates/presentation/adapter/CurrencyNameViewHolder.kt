package com.pakollya.exchangerates.presentation.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.data.database.name.CurrencyName
import com.pakollya.exchangerates.databinding.ListItemCurrencyNameBinding
import com.pakollya.exchangerates.domain.CurrencyInteractor
import com.pakollya.exchangerates.presentation.base.BaseViewHolder
import kotlinx.coroutines.launch

class CurrencyNameViewHolder(
    private val binding: ListItemCurrencyNameBinding,
    private val interactor: CurrencyInteractor,
    private val itemClick: (String) -> Unit
) : BaseViewHolder<ListItemCurrencyNameBinding, CurrencyName>(binding) {

    override fun onBind(item: CurrencyName) {
        with(binding) {
            name.text = item.name
            fullName.text = item.fullName.toString()
            currencyNameItem.setOnClickListener {
                selectItem(item.name)
            }
        }
        val lifecycleOwner = binding.root.context as LifecycleOwner
        lifecycleOwner.lifecycleScope.launch{
            lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                interactor.getBaseName().collect{ baseName ->
                    val selected = baseName == item.name
                    binding.checkImage.visibility = if (selected) View.VISIBLE else View.INVISIBLE
                    binding.name.setTextColor(ContextCompat.getColor(
                        binding.root.context,
                        if (selected) R.color.sortedByCheck else R.color.black
                    ))
                    binding.fullName.setTextColor(ContextCompat.getColor(
                        binding.root.context,
                        if (selected) R.color.sortedByCheck else R.color.black
                    ))
                    binding.currencyNameItem.setBackgroundColor(ContextCompat.getColor(
                        binding.root.context,
                        if (selected) R.color.white else R.color.background
                    ))
                }
            }
        }

    }

    private fun selectItem(itemName: String) {
        itemClick(itemName)
    }
}