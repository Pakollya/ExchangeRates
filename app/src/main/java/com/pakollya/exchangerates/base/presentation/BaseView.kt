package com.pakollya.exchangerates.base.presentation

import android.view.View
import androidx.annotation.DrawableRes

interface BaseView {

    fun show(text: CharSequence) = Unit

    fun show(textId: Int) = Unit

    fun loadImage(url: String) = Unit

    fun showImageResource(@DrawableRes id: Int) = Unit

    fun enable(enabled: Boolean) = Unit

    fun select(selected: Boolean) = Unit

    fun handleClick(listener: View.OnClickListener) = Unit
}