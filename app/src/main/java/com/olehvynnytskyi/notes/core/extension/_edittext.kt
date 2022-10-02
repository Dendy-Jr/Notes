package com.olehvynnytskyi.notes.core.extension

import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.getSystemService

fun EditText.showKeyboard() {
    requestFocus()
    context.getSystemService<InputMethodManager>()
        ?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.hideKeyboard() {
    clearFocus()
    context.getSystemService<InputMethodManager>()
        ?.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
}