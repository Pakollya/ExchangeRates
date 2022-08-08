package com.pakollya.exchangerates.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.pakollya.exchangerates.data.repository.CurrencyRepository
import com.pakollya.exchangerates.data.api.RateApiService
import com.pakollya.exchangerates.data.database.CacheDataSource.BaseDataSource
import com.pakollya.exchangerates.domain.CurrencyInteractor
import com.pakollya.exchangerates.presentation.favorite.FavoritesCurrencyListFragment
import com.pakollya.exchangerates.presentation.list.CurrencyListFragment
import com.pakollya.exchangerates.presentation.name.CurrencyNameListFragment
import com.pakollya.exchangerates.presentation.view.SortedByBottomSheetFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApiModule::class, DatabaseModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {
    val rateApiService: RateApiService
    val appDatabase: BaseDataSource
    val currencyRepository: CurrencyRepository
    val interactor: CurrencyInteractor
    val viewModelFactory: ViewModelProvider.Factory

    fun inject(fragment: CurrencyListFragment)
    fun inject(fragment: SortedByBottomSheetFragment)
    fun inject(fragment: CurrencyNameListFragment)
    fun inject(fragment: FavoritesCurrencyListFragment)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}