package com.olehvynnytskyi.notes.presentation.utils

import android.view.View
import android.view.ViewGroup
import com.olehvynnytskyi.notes.presentation.base.extensions.getIdName
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun parentMatcher(
    childId: Int,
    parentId: Int
): Matcher<View> = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description) {
        description.appendText(
            "Match childId '${getIdName(childId)}' by " +
                    "parentId '${getIdName(parentId)}'"
        )
    }

    override fun matchesSafely(view: View): Boolean {
        if (view.id == childId) {
            var parent: View? = view.parent as? ViewGroup
            if (parent?.id == parentId) return true

            var attempt = 10
            while (parent == null || attempt > 0) {
                parent = parent?.parent as? ViewGroup ?: break
                if (parent.id == parentId) break
                attempt--
            }
            return parent?.id == parentId
        }
        return false
    }
}