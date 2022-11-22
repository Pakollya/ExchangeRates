package com.pakollya.exchangerates

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pakollya.exchangerates.main.presentation.MainActivity
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
class CurrenciesFragmentTest : BaseTest() {

    private lateinit var date: String
    private lateinit var delay: ViewDelay

    @Before
    fun init() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        date = dateFormat.format(Date()).toString()
        delay = ViewDelay(5000)
    }

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_currencies() {
        onView(withId(R.id.progress)).check(matches(isDisplayed()))
        onView(isRoot()).perform(delay)

        onView(withId(R.id.progress)).check(matches(not(isDisplayed())))
        onView(withId(R.id.baseButton)).checkText("EUR")
        viewInRecycler(R.id.currenciesList, 0, R.id.date).checkText("Last update: $date")
        viewInRecycler(R.id.currenciesList, 1, R.id.name).checkText("AED")
        viewInRecycler(R.id.currenciesList, 2, R.id.name).checkText("AFN")
    }

    @Test
    fun test_sort() {
        onView(withId(R.id.sortButton)).click()

        viewInRecycler(R.id.sortingList, 0, R.id.sorting_image).check(matches(isDisplayed()))
        viewInRecycler(R.id.sortingList, 0, R.id.sorting_text).checkText("by name")
        viewInRecycler(R.id.sortingList, 1, R.id.sorting_image).check(matches(not(isDisplayed())))
        viewInRecycler(R.id.sortingList, 1, R.id.sorting_text).checkText("by name desc")

        viewInRecycler(R.id.sortingList, 1, R.id.sorting_item).click()

        pressBack()
        viewInRecycler(R.id.currenciesList, 1, R.id.name).checkText("ZWL")

        onView(withId(R.id.sortButton)).click()
        viewInRecycler(R.id.sortingList, 0, R.id.sorting_item).click()
        pressBack()

        viewInRecycler(R.id.currenciesList, 1, R.id.name).checkText("AED")
        viewInRecycler(R.id.currenciesList, 2, R.id.name).checkText("AFN")
    }

    @Test
    fun test_favorites() {
        viewInRecycler(R.id.currenciesList, 1, R.id.favorite).click()
        viewInRecycler(R.id.currenciesList, 2, R.id.favorite).click()

        onView(withId(R.id.favoritesFragment)).click()

        onView(withId(R.id.baseButton)).checkText("EUR")
        viewInRecycler(R.id.currenciesList, 0, R.id.date).checkText("Last update: $date")
        viewInRecycler(R.id.currenciesList, 1, R.id.name).checkText("AED")
        viewInRecycler(R.id.currenciesList, 1, R.id.imageFavorite).check(matches(isDisplayed()))
        viewInRecycler(R.id.currenciesList, 2, R.id.name).checkText("AFN")
        viewInRecycler(R.id.currenciesList, 2, R.id.imageFavorite).check(matches(isDisplayed()))

        pressBack()
    }

    @Test
    fun test_base() {
        onView(withId(R.id.baseButton)).checkText("EUR")
        onView(withId(R.id.baseButton)).click()

        onView(withId(R.id.progress)).check(matches(isDisplayed()))
        onView(isRoot()).perform(delay)

        onView(withId(R.id.progress)).check(matches(not(isDisplayed())))
        viewInRecycler(R.id.namesList, 0, R.id.name).checkText("AED")
        viewInRecycler(R.id.namesList, 1, R.id.name).checkText("AFN")
        viewInRecycler(R.id.namesList, 1, R.id.name_item).click()
        viewInRecycler(R.id.namesList, 1, R.id.check_image).check(matches(isDisplayed()))

        pressBack()
        onView(isRoot()).perform(delay)

        onView(withId(R.id.progress)).check(matches(not(isDisplayed())))
        onView(withId(R.id.baseButton)).checkText("AFN")
        viewInRecycler(R.id.currenciesList, 0, R.id.date).checkText("Last update: $date")
        viewInRecycler(R.id.currenciesList, 2, R.id.name).checkText("AFN")
        viewInRecycler(R.id.currenciesList, 2, R.id.value).checkText("1.0")
    }
}