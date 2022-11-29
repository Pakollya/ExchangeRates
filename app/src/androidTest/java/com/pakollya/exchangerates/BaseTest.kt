package com.pakollya.exchangerates

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Matcher

abstract class BaseTest {

    protected fun ViewInteraction.checkText(value: String) {
        check(ViewAssertions.matches(ViewMatchers.withText(value)))
    }

    protected fun ViewInteraction.click() {
        perform(ViewActions.click())
    }

    protected fun viewInRecycler(recyclerViewId: Int, position: Int, viewId: Int): ViewInteraction =
        Espresso.onView(RecyclerViewMatcher(recyclerViewId).atPosition(position, viewId))

    protected class ViewDelay(
        private val delay: Long
    ) : ViewAction {

        override fun getConstraints(): Matcher<View> = isRoot()

        override fun getDescription(): String = "wait for $delay milliseconds"

        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(delay)
        }
    }
}