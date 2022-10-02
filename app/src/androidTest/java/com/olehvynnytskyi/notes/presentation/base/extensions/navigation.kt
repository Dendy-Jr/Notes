package com.olehvynnytskyi.notes.presentation.base.extensions

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.olehvynnytskyi.notes.presentation.MainActivity
import com.olehvynnytskyi.notes.presentation.base.BaseTest
import org.junit.Assert
import com.olehvynnytskyi.notes.R
import kotlinx.coroutines.delay
import kotlin.reflect.KClass

/**
 * --Example--
 * startScreen(R.id.addNoteFragment)
 */
suspend fun BaseTest.startScreen(
    @IdRes startDestination: Int,
    args: Bundle? = null,
): ActivityScenario<out Activity> {
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            MainActivity::class.java
        )
    )

    val scenario = ActivityScenario.launch<MainActivity>(mainActivityIntent)
        .onActivity { activity ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity.window.decorView.importantForAutofill =
                    View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
            }

            val navHost = activity.supportFragmentManager.findFragmentById(R.id.navContainer)
                    as NavHostFragment
            val navGraph = navHost.navController.navInflater.inflate(R.navigation.nav_graph)
            navGraph.setStartDestination(startDestination)
            navHost.navController.setGraph(navGraph, args)
        }

    InstrumentationRegistry.getInstrumentation().waitForIdleSync()
//    while (!idleResource.isIdleNow) {
//        delay(100)
//    }
    // Wait until dialogs are inflated
    repeat(getActivityInstance().fragments.filterIsInstance<DialogFragment>().size) {
        delay(100)
    }

    return scenario
}

/**
 * --Example--
 * assertFragmentIs(AddNoteFragment::class)
 */
fun assertFragmentIs(fragmentKClass: KClass<out Fragment>, arguments: Bundle? = null) {
    val activity = getActivityInstance()
    val fragments = activity.fragments
    val condition = fragments.any { fragment ->
        val foundFragment = fragment::class == fragmentKClass
        if (foundFragment && arguments != null) {
            val argumentsEquals = equalsBundles(fragment.arguments, arguments)
            Assert.assertTrue(
                "Fragment `$fragmentKClass` has different arguments, expected ${arguments}, actual ${fragment.arguments}",
                argumentsEquals
            )
        }
        return@any foundFragment
    }

    Assert.assertTrue(
        "Fragment `$fragmentKClass` not present in `${activity.javaClass.simpleName}` found" +
//                "\n${activity.logFragments()}",
                "",

        condition
    )
}

private fun getActivityInstance(): FragmentActivity {
    var currentActivity: FragmentActivity? = null

    InstrumentationRegistry.getInstrumentation().runOnMainSync {
        val resumedActivity =
            ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
        val it = resumedActivity.iterator()
        currentActivity = it.next() as? FragmentActivity
    }
    return currentActivity as FragmentActivity
}

private val FragmentActivity.fragments: List<Fragment>
    get() = supportFragmentManager.findFragments()

private fun FragmentManager.findFragments(): List<Fragment> =
    fragments.map { fragment ->
        listOf(fragment) + fragment.childFragmentManager.findFragments()
    }.flatten()

private fun equalsBundles(a: Bundle?, b: Bundle?): Boolean {
    val aks = a?.keySet() ?: emptySet()
    val bks = b?.keySet() ?: emptySet()
    if (!aks.containsAll(bks)) return false
    for (key in aks) {
        if (a?.get(key) != b?.get(key)) return false
    }
    return true
}

//private fun FragmentActivity.logFragments(): String =
//    StringBuilder("Activity ${this.javaClass.simpleName} fragments:")
//        .apply {
//            supportFragmentManager.fragments.reversed().forEach { fragment ->
//                append("\n")
//                append(fragment.log("-> "))
//            }
//        }
//        .toString()