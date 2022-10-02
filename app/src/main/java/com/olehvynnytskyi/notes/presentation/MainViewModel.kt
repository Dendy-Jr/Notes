package com.olehvynnytskyi.notes.presentation

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    var navController: NavController? = null

    override fun onActive() {
        super.onActive()

        viewModelScope.launch {
            navController?.navigate(R.id.notesFragment)
        }
    }
}