package com.olehvynnytskyi.notes.core.extension

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

fun View.hideKeyboard() {
    clearFocus()
    context.getSystemService<InputMethodManager>()
        ?.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun View.setVisibility(visible: Boolean) {
    if (visible) this.visibility = View.VISIBLE else this.visibility = View.INVISIBLE
}