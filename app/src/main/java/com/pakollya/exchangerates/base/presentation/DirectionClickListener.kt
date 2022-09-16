package com.pakollya.exchangerates.base.presentation

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDirections

interface DirectionClickListener {

    fun apply(view: View, navController: NavController, direction: NavDirections)

    class Base : DirectionClickListener {

        override fun apply(view: View, navController: NavController, direction: NavDirections) {
            view.setOnClickListener {
                navController.navigate(direction)
            }
        }
    }
}