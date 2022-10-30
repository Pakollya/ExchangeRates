package com.pakollya.exchangerates.currencies.presentation

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDirections

interface NavigateOnClick {
    fun navigateOnClick(view: View, navController: NavController, direction: NavDirections)
}