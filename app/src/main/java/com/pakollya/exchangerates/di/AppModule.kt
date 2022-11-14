package com.pakollya.exchangerates.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pakollya.exchangerates.BuildConfig
import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.core.ResourcesManager
import com.pakollya.exchangerates.base.data.*
import com.pakollya.exchangerates.base.domain.HandleDomainError
import com.pakollya.exchangerates.base.presentation.*
import com.pakollya.exchangerates.currencies.data.BaseCurrenciesRepository
import com.pakollya.exchangerates.currencies.data.cache.BaseCurrencyCacheDataSource
import com.pakollya.exchangerates.currencies.data.cache.CurrenciesCache
import com.pakollya.exchangerates.currencies.data.cache.CurrenciesCacheDataSource
import com.pakollya.exchangerates.currencies.data.cache.CurrencyCache.Currencies
import com.pakollya.exchangerates.currencies.data.cache.CurrencyCache.Mapper
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesApiService
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloud
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloudDataSource
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesService
import com.pakollya.exchangerates.currencies.domain.CurrenciesDomain
import com.pakollya.exchangerates.currencies.domain.CurrenciesInteractor
import com.pakollya.exchangerates.currencies.domain.CurrenciesRepository
import com.pakollya.exchangerates.currencies.presentation.CurrenciesCommunications
import com.pakollya.exchangerates.currencies.presentation.CurrenciesListCommunication
import com.pakollya.exchangerates.currencies.presentation.CurrenciesViewModel
import com.pakollya.exchangerates.currencies.presentation.CurrenсiesUi
import com.pakollya.exchangerates.favorites.data.FavoriteCurrencies
import com.pakollya.exchangerates.favorites.data.FavoriteMapper
import com.pakollya.exchangerates.favorites.data.FavoritesCacheDataSource
import com.pakollya.exchangerates.favorites.presentation.ChangeFavorite
import com.pakollya.exchangerates.favorites.presentation.FavoritesCommunication
import com.pakollya.exchangerates.favorites.presentation.FavoritesViewModel
import com.pakollya.exchangerates.main.presentation.MainViewModel
import com.pakollya.exchangerates.names.data.BaseCurrency
import com.pakollya.exchangerates.names.data.BaseCurrencyCommunication
import com.pakollya.exchangerates.names.data.BaseCurrencyNamesRepository
import com.pakollya.exchangerates.names.data.cache.CurrencyNamesCacheDataSource
import com.pakollya.exchangerates.names.data.cloud.CurrencyNameApiService
import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud
import com.pakollya.exchangerates.names.data.cloud.CurrencyNamesCloudDataSource
import com.pakollya.exchangerates.names.data.cloud.CurrencyNamesService
import com.pakollya.exchangerates.names.domain.CurrencyNameDomain
import com.pakollya.exchangerates.names.domain.CurrencyNamesRepository
import com.pakollya.exchangerates.names.domain.NamesInteractor
import com.pakollya.exchangerates.names.presentation.*
import com.pakollya.exchangerates.sorting.data.BaseSortingRepository
import com.pakollya.exchangerates.sorting.data.CurrencySorting
import com.pakollya.exchangerates.sorting.data.SortCacheDataSource
import com.pakollya.exchangerates.sorting.data.UpdateSorting
import com.pakollya.exchangerates.sorting.domain.SortingDomain
import com.pakollya.exchangerates.sorting.domain.SortingInteractor
import com.pakollya.exchangerates.sorting.domain.SortingRepository
import com.pakollya.exchangerates.sorting.presentation.ChangeSort
import com.pakollya.exchangerates.sorting.presentation.SortingCommunications
import com.pakollya.exchangerates.sorting.presentation.SortingListCommunication
import com.pakollya.exchangerates.sorting.presentation.SortingViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class ErrorModule {

    @Provides
    @Singleton
    fun provideHandleDomainError(): HandleError = HandleDomainError()

    @Provides
    @Singleton
    fun provideHandleUiError(
        resourcesManager: ResourcesManager,
        errorCommunication: ErrorCommunication,
    ): HandleUiErrorAbstract = HandleUiError(resourcesManager, errorCommunication)
}

@Module
class CloudModule {

    @Provides
    @Singleton
    @Debug
    fun provideInterceptorDebug(): Interceptor = Interceptor.Debug()

    @Provides
    @Singleton
    @Release
    fun provideInterceptorRelease(): Interceptor = Interceptor.Release()

    @Provides
    @Singleton
    fun provideConverterFactory(): ConverterFactory = ConverterFactory.Base()

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(
        @Debug
        interceptorDebug: Interceptor,
        @Release
        interceptorRelease: Interceptor,
    ): OkHttpClientBuilder = OkHttpClientBuilder.Base(
        if (BuildConfig.DEBUG)
            interceptorDebug
        else
            interceptorRelease
    )

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        converterFactory: ConverterFactory,
        okHttpClientBuilder: OkHttpClientBuilder,
    ): RetrofitBuilder = RetrofitBuilder.Base(
        converterFactory,
        okHttpClientBuilder
    )

    @Provides
    @Singleton
    fun provideExchangeRatesApiService(
        retrofitBuilder: RetrofitBuilder,
    ): ExchangeRatesApiService = ExchangeRatesApiService.Base(
        retrofitBuilder
    )

    @Provides
    @Singleton
    fun provideCurrencyNameApiService(
        retrofitBuilder: RetrofitBuilder,
    ): CurrencyNameApiService = CurrencyNameApiService.Base(
        retrofitBuilder
    )

    @Provides
    @Singleton
    fun provideExchangeRatesService(
        apiService: ExchangeRatesApiService,
    ): ExchangeRatesService = apiService.exchangeRatesService()

    @Provides
    @Singleton
    fun provideCurrencyNamesService(
        apiService: CurrencyNameApiService,
    ): CurrencyNamesService = apiService.currencyNamesService()

    @Provides
    @Singleton
    fun provideExchangeRatesCloudDataSource(
        service: ExchangeRatesService,
        handleDomainError: HandleError,
    ): ExchangeRatesCloudDataSource = ExchangeRatesCloudDataSource.Base(
        service, handleDomainError
    )

    @Provides
    @Singleton
    fun provideCurrencyNameCloudDataSource(
        nameService: CurrencyNamesService,
        handleDomainError: HandleError,
    ): CurrencyNamesCloudDataSource = CurrencyNamesCloudDataSource.Base(
        nameService, handleDomainError
    )
}

@Module
class CacheModule {

    @Provides
    @Singleton
    @Base
    fun provideBaseDataStore(
        context: Context,
    ): PreferenceDataStore<String> = PreferenceDataStore.Base(
        context.getSharedPreferences(BASE_CURRENCY_KEY, Context.MODE_PRIVATE)
    )

    @Provides
    @Singleton
    @Sorting
    fun provideSortingDataStore(
        context: Context,
    ): PreferenceDataStore<String> = PreferenceDataStore.Base(
        context.getSharedPreferences(CURRENCY_SORTING_KEY, Context.MODE_PRIVATE)
    )

    @Provides
    @Singleton
    fun provideFavoriteCurrencies(
        context: Context,
    ): FavoriteCurrencies.Mutable = FavoriteCurrencies.Base(
        PreferenceDataStore.Favorites(
            context.getSharedPreferences(FAVORITE_KEY, Context.MODE_PRIVATE)
        )
    )

    @Provides
    @Singleton
    fun provideBaseCurrency(
        @Base
        baseDataStore: PreferenceDataStore<String>,
    ): BaseCurrency.Mutable = BaseCurrency.Base(baseDataStore)

    @Provides
    @Singleton
    fun provideCurrencySorting(
        @Sorting
        sortingDataStore: PreferenceDataStore<String>,
    ): CurrencySorting.Mutable = CurrencySorting.Base(sortingDataStore)

    @Provides
    @Singleton
    fun provideFavoritesCacheDataSource(
        favoriteCurrencies: FavoriteCurrencies.Mutable,
    ): FavoritesCacheDataSource = FavoritesCacheDataSource.Base(favoriteCurrencies)

    @Provides
    @Singleton
    fun provideBaseCurrencyCacheDataSource(
        baseCurrency: BaseCurrency.Mutable,
    ): BaseCurrencyCacheDataSource = BaseCurrencyCacheDataSource.Base(baseCurrency)

    @Provides
    @Singleton
    fun provideSortCacheDataSource(
        currencySorting: CurrencySorting.Mutable,
    ): SortCacheDataSource = SortCacheDataSource.Base(currencySorting)

    @Provides
    @Singleton
    fun provideCurrenciesCache(context: Context): CurrenciesCache =
        CurrenciesCache.Base(context.applicationContext)

    @Provides
    @Singleton
    fun provideCurrenciesCacheDataSource(
        currenciesCache: CurrenciesCache,
        mapper: ExchangeRatesCloud.Mapper<Currencies>,
    ): CurrenciesCacheDataSource = CurrenciesCacheDataSource.Base(
        currenciesCache.dataBase().currencyDao(),
        mapper
    )

    @Provides
    @Singleton
    fun provideCurrencyNamesCacheDataSource(
        currenciesCache: CurrenciesCache,
    ): CurrencyNamesCacheDataSource = CurrencyNamesCacheDataSource.Base(
        currenciesCache.dataBase().currencyNameDao()
    )

    companion object {
        private const val FAVORITE_KEY = "FavoriteKey"
        private const val BASE_CURRENCY_KEY = "BaseCurrencyKey"
        private const val CURRENCY_SORTING_KEY = "CurrencySortingKey"
    }
}

@Module
class MapperModule {

    @Provides
    @Singleton
    fun provideMapper(): ExchangeRatesCloud.Mapper<Currencies> = ExchangeRatesCloud.Mapper.Base()

    @Provides
    @Singleton
    fun provideCurrenciesMapper(): Mapper<CurrenciesDomain> = Mapper.Base()

    @Provides
    @Singleton
    fun provideNameMapper(): CurrencyNameCloud.Mapper<CurrencyNameDomain> =
        CurrencyNameCloud.Mapper.Base()

    @Provides
    @Singleton
    fun provideFavoriteMapper(
        favoritesCache: FavoritesCacheDataSource,
    ): FavoriteMapper = FavoriteMapper.Base(favoritesCache)

    @Provides
    @Singleton
    fun provideCurrencyDomainMapper(
        favoritesCache: FavoritesCacheDataSource,
        changeFavorite: ChangeFavorite,
    ): CurrenciesDomain.Mapper<CurrenсiesUi> =
        CurrenciesDomain.Mapper.Base(favoritesCache, changeFavorite)

    @Provides
    @Singleton
    fun provideNameDomainMapper(
        baseCurrencyCache: BaseCurrencyCacheDataSource,
        changeBaseCurrency: ChangeBaseCurrency,
    ): CurrencyNameDomain.Mapper<ItemsUi> =
        CurrencyNameDomain.Mapper.Base(baseCurrencyCache, changeBaseCurrency)

    @Provides
    @Singleton
    fun provideSortingDomainMapper(
        sortCache: SortCacheDataSource,
        changeSort: ChangeSort,
    ): SortingDomain.Mapper<ItemsUi> = SortingDomain.Mapper.Base(sortCache, changeSort)
}

@Module
class CommunicationModule {

    @Provides
    @Singleton
    fun provideChangeFavorite(
        favoritesCache: FavoritesCacheDataSource,
        updateSorting: UpdateSorting,
    ): ChangeFavorite = ChangeFavorite.Base(favoritesCache, updateSorting)

    @Provides
    @Singleton
    fun provideChangeBaseCurrency(
        baseCurrency: BaseCurrencyCacheDataSource,
        communication: BaseCurrencyCommunication,
    ): ChangeBaseCurrency = ChangeBaseCurrency.Base(baseCurrency, communication)

    @Provides
    @Singleton
    fun provideChangeSort(
        sortCache: SortCacheDataSource,
        updateSorting: UpdateSorting,
    ): ChangeSort = ChangeSort.Base(sortCache, updateSorting)

    @Provides
    @Singleton
    fun provideErrorCommunication(): ErrorCommunication = ErrorCommunication.Base()

    @Provides
    @Singleton
    fun provideProgressCommunication(): ProgressCommunication = ProgressCommunication.Base()

    @Provides
    @Singleton
    fun provideBaseCurrencyCommunication(): BaseCurrencyCommunication =
        BaseCurrencyCommunication.Base()

    @Provides
    @Singleton
    fun provideBottomNavigationCommunication(): BottomNavigationCommunication =
        BottomNavigationCommunication.Base()

    @Provides
    @Singleton
    fun provideUpdateSorting(): UpdateSorting = UpdateSorting.Base()

    @Provides
    @Singleton
    fun provideCurrenciesListCommunication(): CurrenciesListCommunication =
        CurrenciesListCommunication.Base()

    @Provides
    @Singleton
    fun provideFavoritesCommunication(): FavoritesCommunication = FavoritesCommunication.Base()

    @Provides
    @Singleton
    fun provideNamesListCommunication(): NamesListCommunication = NamesListCommunication.Base()

    @Provides
    @Singleton
    fun provideSortingCommunication(): SortingListCommunication = SortingListCommunication.Base()

    @Provides
    @Singleton
    @Base
    fun provideCurrenciesCommunications(
        navigation: BottomNavigationCommunication,
        progressCommunication: ProgressCommunication,
        updateSorting: UpdateSorting,
        errorCommunication: ErrorCommunication,
        currenciesListCommunication: CurrenciesListCommunication,
    ): CurrenciesCommunications = CurrenciesCommunications.Base(
        navigation,
        progressCommunication,
        updateSorting,
        errorCommunication,
        currenciesListCommunication
    )

    @Provides
    @Singleton
    @Favorite
    fun provideFavoriteCurrenciesCommunications(
        navigation: BottomNavigationCommunication,
        progressCommunication: ProgressCommunication,
        updateSorting: UpdateSorting,
        errorCommunication: ErrorCommunication,
        favoritesCommunication: FavoritesCommunication,
    ): CurrenciesCommunications = CurrenciesCommunications.Favorite(
        navigation,
        progressCommunication,
        updateSorting,
        errorCommunication,
        favoritesCommunication
    )

    @Provides
    @Singleton
    fun provideNamesCommunications(
        navigation: BottomNavigationCommunication,
        progressCommunication: ProgressCommunication,
        baseCurrency: BaseCurrencyCommunication,
        namesList: NamesListCommunication,
    ): NamesCommunications = NamesCommunications.Base(
        navigation, progressCommunication, baseCurrency, namesList
    )

    @Provides
    @Singleton
    fun provideSortingCommunications(
        updateSorting: UpdateSorting,
        listCommunication: SortingListCommunication,
    ): SortingCommunications = SortingCommunications.Base(
        updateSorting, listCommunication
    )
}

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Base
    fun provideCurrenciesRepository(
        baseCurrencyCache: BaseCurrencyCacheDataSource,
        sortCache: SortCacheDataSource,
        currenciesCache: CurrenciesCacheDataSource,
        currenciesCloud: ExchangeRatesCloudDataSource,
        mapper: Mapper<CurrenciesDomain>,
    ): CurrenciesRepository = BaseCurrenciesRepository(
        baseCurrencyCache, sortCache, currenciesCache,
        currenciesCloud, mapper
    )

    @Provides
    @Singleton
    @Favorite
    fun provideFavoritesCurrenciesRepository(
        baseCurrencyCache: BaseCurrencyCacheDataSource,
        sortCache: SortCacheDataSource,
        currenciesCache: CurrenciesCacheDataSource,
        currenciesCloud: ExchangeRatesCloudDataSource,
        mapper: FavoriteMapper,
    ): CurrenciesRepository = BaseCurrenciesRepository(
        baseCurrencyCache, sortCache, currenciesCache,
        currenciesCloud, mapper
    )

    @Provides
    @Singleton
    fun provideCurrencyNameRepository(
        namesCache: CurrencyNamesCacheDataSource,
        namesCloud: CurrencyNamesCloudDataSource,
        mapper: CurrencyNameCloud.Mapper<CurrencyNameDomain>,
    ): CurrencyNamesRepository = BaseCurrencyNamesRepository(
        namesCache, namesCloud, mapper
    )

    @Provides
    @Singleton
    fun provideSortingRepository(): SortingRepository = BaseSortingRepository()
}

@Module
class InteractorModule {

    @Provides
    @Singleton
    @Base
    fun provideCurrenciesInteractor(
        mapper: CurrenciesDomain.Mapper<CurrenсiesUi>,
        @Base
        repository: CurrenciesRepository,
        dispatchers: Dispatchers,
        handleError: HandleUiErrorAbstract,
    ): CurrenciesInteractor = CurrenciesInteractor.Base(
        mapper, repository, dispatchers, handleError
    )

    @Provides
    @Singleton
    @Favorite
    fun provideFavoritesInteractor(
        mapper: CurrenciesDomain.Mapper<CurrenсiesUi>,
        @Favorite
        repository: CurrenciesRepository,
        dispatchers: Dispatchers,
        handleError: HandleUiErrorAbstract,
    ): CurrenciesInteractor = CurrenciesInteractor.Base(
        mapper, repository, dispatchers, handleError
    )

    @Provides
    @Singleton
    fun provideNamesInteractor(
        mapper: CurrencyNameDomain.Mapper<ItemsUi>,
        repository: CurrencyNamesRepository,
        dispatchers: Dispatchers,
        handleError: HandleUiErrorAbstract,
    ): NamesInteractor = NamesInteractor.Base(
        mapper, repository, dispatchers, handleError
    )

    @Provides
    @Singleton
    fun provideSortingInteractor(
        sortingDomainMapper: SortingDomain.Mapper<ItemsUi>,
        repository: SortingRepository,
        dispatchers: Dispatchers,
        handleError: HandleUiErrorAbstract,
    ): SortingInteractor = SortingInteractor.Base(
        sortingDomainMapper, repository, dispatchers, handleError
    )
}

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideDispatchers(): Dispatchers = Dispatchers.Base()

    @Provides
    @Singleton
    fun provideResourcesManager(context: Context): ResourcesManager = ResourcesManager.Base(context)
}

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CurrenciesViewModel::class)
    internal abstract fun bindCurrenciesViewModel(viewModel: CurrenciesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    internal abstract fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NamesViewModel::class)
    internal abstract fun bindNamesViewModel(viewModel: NamesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SortingViewModel::class)
    internal abstract fun bindSortingViewModel(viewModel: SortingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}

@Qualifier
annotation class Base

@Qualifier
annotation class Favorite

@Qualifier
annotation class Sorting

@Qualifier
annotation class Debug

@Qualifier
annotation class Release