package com.olehvynnytskyi.notes.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import java.lang.RuntimeException

fun PerformOnChildAction(@IdRes childId: Int, action: ViewAction) = object : ViewAction {
    override fun getConstraints(): Matcher<View> =
        Matchers.allOf(withEffectiveVisibility(Visibility.VISIBLE))

    override fun getDescription() = "click on a child view"

    override fun perform(uiController: UiController, view: View) {
        action.perform(uiController, view.findViewById(childId))
    }
}

fun ScrollToAction() = object : ViewAction {
    override fun getConstraints(): Matcher<View> =
        Matchers.allOf(
            withEffectiveVisibility(Visibility.VISIBLE),
        )

    override fun getDescription(): String = "scroll to"

    override fun perform(uiController: UiController, view: View) {
        if (isDisplayingAtLeast(90).matches(view)) {
            return
        }
        val rect = Rect()
        view.getDrawingRect(rect)
        if (!view.requestRectangleOnScreen(rect, true)) {
            // Scrolling to view was requested, but none of the parents scrolled.
            return
        }
        uiController.loopMainThreadUntilIdle()
        if (!isDisplayingAtLeast(90).matches(view)) {
            throw PerformException.Builder()
                .withActionDescription(this.description)
                .withViewDescription(HumanReadables.describe(view))
                .withCause(
                    RuntimeException(
                        "Scrolling to view was attempted, but the view is not displayed"
                    )
                )
                .build()
        }
    }
}