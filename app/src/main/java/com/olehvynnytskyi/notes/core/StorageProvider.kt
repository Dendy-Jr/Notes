package com.olehvynnytskyi.notes.core

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageProvider @Inject constructor(
    private val application: Application
) {

    fun getStorage(key: String): SharedPreferences {
        return application.getSharedPreferences(key, Context.MODE_PRIVATE)
    }
}