package com.olehvynnytskyi.notes.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.olehvynnytskyi.notes.databinding.ToolbarBinding
import com.olehvynnytskyi.notes.presentation.base.ViewModelOwner

class Toolbar(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {

    private val binding = ToolbarBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        doOnNextLayout {
            onBackClicked {
                (FragmentManager.findFragment<Fragment>(this) as? ViewModelOwner<*>)
                    ?.viewModel?.navigateBack()
            }
        }
    }

    fun setActionIcon(@DrawableRes resId: Int) {
        binding.imageAction.setImageResource(resId)
    }

    fun setActionListener(listener: () -> Unit) {
        binding.imageAction.setOnClickListener { listener() }
    }

    private fun onBackClicked(listener: () -> Unit) {
        binding.imageBack.setOnClickListener { listener() }
    }
}