package com.olehvynnytskyi.notes.presentation.base.extensions

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.olehvynnytskyi.notes.presentation.utils.ScrollToAction
import com.olehvynnytskyi.notes.presentation.utils.parentMatcher
import org.hamcrest.Matchers

/**
 * --Example--
 * clickOn(viewId = R.id.ivRedShape)
 */
fun clickOn(@IdRes viewId: Int, @IdRes parentId: Int? = null) {
    perform(viewId, parentId, ViewActions.click())
}

/**
 * --Example--
 * assertHasText(viewId = R.id.tvHeader, text = getString(R.string.app_name))
 */
fun assertHasText(@IdRes viewId: Int, text: String, @IdRes parentId: Int? = null) {
    val matcher = Matchers.allOf(
        if (parentId != null) {
            parentMatcher(viewId, parentId)
        } else {
            ViewMatchers.withId(viewId)
        },
        ViewMatchers.withText(text)
    )
    Espresso.onView(matcher)
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

/**
 * --Example--
 * assertIsVisible(viewId = R.id.rgNoteOrder, isVisible = true)
 */
fun assertIsVisible(@IdRes viewId: Int, isVisible: Boolean, @IdRes parentId: Int? = null) {
    val matcher = Matchers.allOf(
        if (parentId != null) {
            parentMatcher(viewId, parentId)
        } else {
            ViewMatchers.withId(viewId)
        },
    )
    Espresso.onView(matcher)
        .check(
            ViewAssertions.matches(
                if (isVisible) ViewMatchers.isDisplayed() else Matchers.not(
                    ViewMatchers.isDisplayed()
                )
            )
        )
}

/**
 * --Example--
 * typeText(viewId = R.id.etAddTitle, text = "Hello World")
 */
fun typeText(
    @IdRes viewId: Int,
    text: String,
    parentId: Int? = null,
    closeKeyboard: Boolean = true
) {
    perform(viewId, parentId, ViewActions.click(), ViewActions.typeText(text))
    if (closeKeyboard) perform(viewId, parentId, ViewActions.closeSoftKeyboard())
}

/**
 * --Example--
 * typeText(viewId = R.id.etAddTitle, text = "Update text in EditText")
 */
fun replaceText(
    @IdRes viewId: Int,
    text: String,
    parentId: Int? = null,
    closeKeyboard: Boolean = true
) {
    perform(viewId, parentId, click(), replaceText(text))
    if (closeKeyboard) perform(viewId, parentId, closeSoftKeyboard())
}

private fun perform(@IdRes viewId: Int, @IdRes parentId: Int?, vararg action: ViewAction) {
    val matcher = if (parentId != null) {
        parentMatcher(viewId, parentId)
    } else {
        ViewMatchers.withId(viewId)
    }
    Espresso.onView(matcher)
        .perform(ScrollToAction())
        .perform(*action)
    InstrumentationRegistry.getInstrumentation().waitForIdleSync()
}