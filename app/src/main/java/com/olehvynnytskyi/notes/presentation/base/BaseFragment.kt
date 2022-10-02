package com.olehvynnytskyi.notes.presentation.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.core.navigation.BackNavDirections
import kotlinx.coroutines.launch

interface ViewBindingOwner<VB : ViewBinding> {
    val binding: VB
}

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId), ViewModelOwner<VM>, ViewBindingOwner<VB> {

    abstract override val viewModel: VM
    abstract override val binding: VB

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    open fun onBackPressed() = viewModel.navigateBack()

    abstract fun onBind()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (onBackPressed()) {
                    onBackPressedCallback.isEnabled = true
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.navigation.collect { navDirections ->
                if (navDirections is BackNavDirections) {
                    onBackPressedCallback.isEnabled = false
                    requireActivity().onBackPressed()
                    return@collect
                }

                try {
                    findNavController().navigate(navDirections)
                } catch (error: Throwable) {
                    requireActivity().findNavController(R.id.navContainer).navigate(navDirections)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onActive()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onInActive()
    }
}