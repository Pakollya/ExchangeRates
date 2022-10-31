package com.pakollya.exchangerates.di

import android.content.Context
import com.pakollya.exchangerates.currencies.presentation.CurrenciesFragment
import com.pakollya.exchangerates.favorites.presentation.FavoritesFragment
import com.pakollya.exchangerates.main.presentation.MainActivity
import com.pakollya.exchangerates.names.presentation.NamesFragment
import com.pakollya.exchangerates.sorting.presentation.SortingBottomSheetFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ErrorModule::class, CloudModule::class, CacheModule::class,
    MapperModule::class, CommunicationModule::class, RepositoryModule::class,
    InteractorModule::class, AppModule::class, ViewModelModule::class
])
@Singleton
interface AppComponent {

    fun inject(fragment: CurrenciesFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: NamesFragment)
    fun inject(fragment: SortingBottomSheetFragment)
    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}