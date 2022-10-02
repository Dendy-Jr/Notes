package com.olehvynnytskyi.notes.presentation.base.extensions

import android.content.res.Resources
import androidx.annotation.IdRes
import androidx.annotation.StringRes

fun getResources(): Resources =
    getContext().resources

fun getString(@StringRes resId: Int): String =
    getContext().getString(resId)

fun getIdName(@IdRes resId: Int): String =
    getResources().getResourceName(resId)