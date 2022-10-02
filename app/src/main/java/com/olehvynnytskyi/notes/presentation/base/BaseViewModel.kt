package com.olehvynnytskyi.notes.presentation.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.olehvynnytskyi.notes.core.navigation.BackNavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

interface ViewModelOwner<VM : BaseViewModel> {
    val viewModel: VM
}

open class BaseViewModel : ViewModel() {

    protected val onActiveScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @CallSuper
    open fun onActive() {
    }

    @CallSuper
    open fun onInActive() {
        onActiveScope.coroutineContext[Job]?.cancelChildren()
    }

    private val _navigation = MutableSharedFlow<NavDirections>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val navigation = _navigation.shareIn(viewModelScope, SharingStarted.Lazily)

    fun navigateTo(navDirections: NavDirections) {
        _navigation.tryEmit(navDirections)
    }

    fun navigateBack(): Boolean {
        viewModelScope.launch {
            _navigation.tryEmit(backDirection())
        }
        return true
    }

    open fun backDirection(): NavDirections = BackNavDirections
}

@HiltViewModel
class EmptyViewModel @Inject constructor() : BaseViewModel()