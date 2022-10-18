package com.pakollya.exchangerates.base.core

import android.content.Context
import androidx.annotation.StringRes

interface ResourcesManager {

    fun string(@StringRes resId: Int): String

    class Base(
        private val context: Context
    ) : ResourcesManager {
        override fun string(resId: Int) = context.getString(resId)
    }
}