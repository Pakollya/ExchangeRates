package com.pakollya.exchangerates.sorting.data

import com.pakollya.exchangerates.sorting.domain.SortingDomain
import com.pakollya.exchangerates.sorting.domain.SortingRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseSortingRepositoryTest {
    private lateinit var repository: SortingRepository

    @Before
    fun init() {
        repository = BaseSortingRepository()
    }

    @Test
    fun `test sorting`() {
        val actual = repository.sorting()
        val expected = SortingDomain.Base(listOf(
            "by name",
            "by name desc",
            "by value",
            "by value desc"
        ))

        assertEquals(expected, actual)
    }
}