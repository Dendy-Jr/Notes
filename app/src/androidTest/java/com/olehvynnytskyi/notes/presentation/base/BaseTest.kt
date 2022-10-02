package com.olehvynnytskyi.notes.presentation.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule

open class BaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        hiltRule.inject()
    }

    fun stage(title: String, block: suspend () -> Unit) = runBlocking {
        try {
            block()
        } catch (error: Throwable) {
            throw Throwable("'$title' stage is failed", error)
        }
    }
}