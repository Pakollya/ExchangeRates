package com.pakollya.exchangerates.base.presentation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.pakollya.exchangerates.App
import com.pakollya.exchangerates.di.AppComponent

inline fun <reified L : Any> Fragment.findImplementationOrThrow(): L {
    return findImplementation(L::class.java)
        ?: throw IllegalStateException("Implementation of ${L::class.java.name} was not found")
}

inline fun <reified L : Any> Fragment.findImplementation(): L? {
    return findImplementation(L::class.java)
}

inline fun <reified T : ViewModel> FragmentActivity.injectViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProvider(this, factory)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.injectViewModel(factory: ViewModelProvider.Factory, owner: ViewModelStoreOwner): T {
    return ViewModelProvider(owner, factory)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.injectViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProvider(this, factory)[T::class.java]
}

fun <L : Any> Fragment.findImplementation(klass: Class<L>): L? {
    val activity = this.activity
    val parentFragment = this.parentFragment

    return when {
        klass.isInstance(parentFragment) -> klass.cast(parentFragment)
        klass.isInstance(activity) && parentFragment == null -> klass.cast(activity)
        else -> parentFragment?.findImplementation(klass)
    }
}

val Context.appComponent: AppComponent
    get() = when(this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }