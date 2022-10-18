package com.pakollya.exchangerates.base.presentation.adapter

import android.view.ViewGroup
import com.pakollya.exchangerates.base.presentation.ItemUi
import java.lang.IllegalStateException

interface ViewHolderFactory<T : ItemUi> {

    fun viewHolder(parent: ViewGroup, viewType: Int) : BaseViewHolder<T>

    class Exception<T : ItemUi> : ViewHolderFactory<T> {
        override fun viewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
            throw IllegalStateException("unknown viewType $viewType")
        }
    }
}