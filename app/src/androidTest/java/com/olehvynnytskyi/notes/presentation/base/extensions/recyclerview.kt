package com.olehvynnytskyi.notes.presentation.base.extensions

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import com.olehvynnytskyi.notes.presentation.utils.PerformOnChildAction
import org.junit.Assert

/**
 * Version #1
 * ---Example---
 *  performOnItemAtPosition(
 *      recyclerViewId = R.id.recyclerView, position = position, action = click()
 *  )
 * @param position -> index in the list
 */
fun performOnItemAtPosition(
    @IdRes recyclerViewId: Int,
    @IdRes childId: Int? = null,
    position: Int,
    action: ViewAction,
) {
    val action = if (childId != null) {
        PerformOnChildAction(childId, action)
    } else {
        action
    }
    Espresso.onView(ViewMatchers.withId(recyclerViewId))
        .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(position, action))
}

/**
 * Version #2
 * ---Example---
 *  onView(withId(R.id.recyclerView)).perform(
 *      RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
 *      position,
 *      clickOnViewChild(R.id.ivDelete)
 *      )
 *  )
 */
fun clickOnViewChild(viewId: Int) = object : ViewAction {
    override fun getConstraints() = null

    override fun getDescription() = "Click on a child view with specified id."

    override fun perform(uiController: UiController, view: View) =
        click().perform(uiController, view.findViewById<View>(viewId))
}

fun performOnViewHolderAtPosition(
    @IdRes recyclerViewId: Int,
    position: Int,
    block: (RecyclerView.ViewHolder) -> Unit,
) {
    Espresso.onView(ViewMatchers.withId(recyclerViewId))
        .check { recyclerView, _ ->
            val viewHolder =
                (recyclerView as RecyclerView).findViewHolderForAdapterPosition(position)!!
            block(viewHolder)
        }
}

/**
 * ---Example---
 *  assertRecyclerViewItemCount(
 *      recyclerViewId = R.id.recyclerView,
 *      itemCount = notes.size
 *  )
 */
fun assertRecyclerViewItemCount(@IdRes recyclerViewId: Int, itemCount: Int) {
    Espresso.onView(ViewMatchers.withId(recyclerViewId)).check { view: View, _ ->
        val adapterItemCount = (view as RecyclerView).adapter!!.itemCount
        Assert.assertEquals(adapterItemCount, itemCount)
    }
}

/**
 * --Example--
 *  assertOnItemAtPosition(
 *      recyclerViewId = R.id.recyclerView,
 *      childId = R.id.tvTitle,
 *      position = 0,
 *      assertion = matches(withText(note.title))
 *  )
 */
fun assertOnItemAtPosition(
    @IdRes recyclerViewId: Int,
    @IdRes childId: Int? = null,
    position: Int,
    assertion: ViewAssertion,
) {
    Espresso.onView(ViewMatchers.withId(recyclerViewId))
        .check { recyclerView, noViewFoundException ->
            val viewHolder =
                (recyclerView as RecyclerView).findViewHolderForAdapterPosition(position)!!
            val view = if (childId != null) {
                viewHolder.itemView.findViewById(childId)
            } else {
                viewHolder.itemView
            }
            assertion.check(view, noViewFoundException)
        }
}