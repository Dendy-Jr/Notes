package com.olehvynnytskyi.notes.presentation.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.core.navigation.BackNavDirections
import kotlinx.coroutines.launch

abstract class BaseDialogFragment<VM : BaseViewModel, VB : ViewBinding>(
    @LayoutRes contentLayoutId: Int
) : DialogFragment(contentLayoutId), ViewModelOwner<VM>, ViewBindingOwner<VB> {

    abstract override val viewModel: VM
    abstract override val binding: VB

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    open fun onBackPressed(): Boolean = viewModel.navigateBack()

    abstract fun onBind()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (onBackPressed()) {
                    onBackPressedCallback.isEnabled = false
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
                    dismissAllowingStateLoss()
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

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)

        val margin = resources.getDimensionPixelSize(R.dimen.dialogMargin)
        val width = resources.displayMetrics.widthPixels - margin * 2
        dialog?.window?.setLayout(width, WRAP_CONTENT)
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