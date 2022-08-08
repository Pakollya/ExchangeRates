package com.pakollya.exchangerates.presentation.adapter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.data.database.currency.Currency
import com.pakollya.exchangerates.databinding.ListItemCurrencyBinding
import com.pakollya.exchangerates.domain.CurrencyInteractor
import com.pakollya.exchangerates.presentation.base.BaseViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CurrencyViewHolder(
    private val binding: ListItemCurrencyBinding,
    private val interactor: CurrencyInteractor,
    private val favoriteClick: (String) -> Unit
) : BaseViewHolder<ListItemCurrencyBinding, Currency>(binding) {

    private val isFavorite: MutableStateFlow<Boolean?> = MutableStateFlow(false)
    private val lifecycleOwner = binding.root.context as LifecycleOwner

    override fun onBind(item: Currency) {
        this.item = item
        setFavorite()
        binding.name.text = item.name
        binding.value.text = item.value.toString()
        binding.favorite.setOnClickListener {
            onFavoriteClicked(item.name)
        }

        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                isFavorite.collect{
                    binding.imageFavorite.setImageResource(
                        if (it == true) R.drawable.ic_favorite else R.drawable.ic_not_favorite
                    )
                }
            }
        }
    }

    private fun onFavoriteClicked(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            isFavorite.value = interactor.setFavoriteByName(name)
            favoriteClick(name)
        }
    }

    private fun setFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            isFavorite.value = interactor.hasCurrencyFavoriteByName(item.name)
        }
    }
}