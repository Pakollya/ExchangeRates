package com.pakollya.exchangerates.names.data.cache

import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud.CurrencyNames
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface CurrencyNamesCacheDataSource {

    suspend fun names(): CurrencyNames

    suspend fun saveNames(names: CurrencyNames)

    class Base(
        private val dao: CurrencyNameDao,
    ) : CurrencyNamesCacheDataSource {

        private val mutex = Mutex()

        override suspend fun names(): CurrencyNames = mutex.withLock {
            val count = dao.namesCount()
            return if (count == null || count < 1)
                CurrencyNames(emptyMap())
            else
                dao.names()
        }

        override suspend fun saveNames(names: CurrencyNames) = mutex.withLock {
            dao.insertNames(names)
        }
    }
}