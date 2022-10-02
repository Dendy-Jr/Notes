package com.olehvynnytskyi.notes.presentation.add_note

import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.core.extension.collectWithLifecycle
import com.olehvynnytskyi.notes.core.extension.getColorFromAttr
import com.olehvynnytskyi.notes.databinding.FragmentAddBinding
import com.olehvynnytskyi.notes.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment :
    BaseFragment<AddNoteViewModel, FragmentAddBinding>(R.layout.fragment_add) {

    override val viewModel: AddNoteViewModel by viewModels()
    override val binding: FragmentAddBinding by viewBinding()

    private var colorNote = R.attr.colorWhite

    override fun onBind(): Unit = with(binding) {

        ivWhiteShape.setOnClickListener {
            colorNote = R.attr.colorWhite
            setBackground(ivWhiteShape)
            ivWhiteShape.setBackgroundResource(R.drawable.white_shape_outlined)
            ivRedShape.setBackgroundResource(R.drawable.red_shape)
            ivBlueShape.setBackgroundResource(R.drawable.blue_shape)
            ivGreenShape.setBackgroundResource(R.drawable.green_shape)
            ivPinkShape.setBackgroundResource(R.drawable.pink_shape)
            ivYellowShape.setBackgroundResource(R.drawable.yellow_shape)
            ivOrangeShape.setBackgroundResource(R.drawable.orange_shape)
        }

        ivRedShape.setOnClickListener {
            colorNote = R.attr.colorRed
            setBackground(ivRedShape)
            ivRedShape.setBackgroundResource(R.drawable.red_shape_outlined)
            ivWhiteShape.setBackgroundResource(R.drawable.white_shape)
            ivBlueShape.setBackgroundResource(R.drawable.blue_shape)
            ivGreenShape.setBackgroundResource(R.drawable.green_shape)
            ivPinkShape.setBackgroundResource(R.drawable.pink_shape)
            ivYellowShape.setBackgroundResource(R.drawable.yellow_shape)
            ivOrangeShape.setBackgroundResource(R.drawable.orange_shape)
        }
        ivBlueShape.setOnClickListener {
            colorNote = R.attr.colorBlue
            setBackground(ivBlueShape)
            ivBlueShape.setBackgroundResource(R.drawable.blue_shape_outlined)
            ivRedShape.setBackgroundResource(R.drawable.red_shape)
            ivWhiteShape.setBackgroundResource(R.drawable.white_shape)
            ivGreenShape.setBackgroundResource(R.drawable.green_shape)
            ivPinkShape.setBackgroundResource(R.drawable.pink_shape)
            ivYellowShape.setBackgroundResource(R.drawable.yellow_shape)
            ivOrangeShape.setBackgroundResource(R.drawable.orange_shape)
        }
        ivGreenShape.setOnClickListener {
            colorNote = R.attr.colorGreen
            setBackground(ivGreenShape)
            ivGreenShape.setBackgroundResource(R.drawable.green_shape_outlined)
            ivRedShape.setBackgroundResource(R.drawable.red_shape)
            ivBlueShape.setBackgroundResource(R.drawable.blue_shape)
            ivWhiteShape.setBackgroundResource(R.drawable.white_shape)
            ivPinkShape.setBackgroundResource(R.drawable.pink_shape)
            ivYellowShape.setBackgroundResource(R.drawable.yellow_shape)
            ivOrangeShape.setBackgroundResource(R.drawable.orange_shape)
        }
        ivPinkShape.setOnClickListener {
            colorNote = R.attr.colorPink
            setBackground(ivPinkShape)
            ivPinkShape.setBackgroundResource(R.drawable.pink_shape_outlined)
            ivRedShape.setBackgroundResource(R.drawable.red_shape)
            ivBlueShape.setBackgroundResource(R.drawable.blue_shape)
            ivGreenShape.setBackgroundResource(R.drawable.green_shape)
            ivWhiteShape.setBackgroundResource(R.drawable.white_shape)
            ivYellowShape.setBackgroundResource(R.drawable.yellow_shape)
            ivOrangeShape.setBackgroundResource(R.drawable.orange_shape)
        }
        ivYellowShape.setOnClickListener {
            colorNote = R.attr.colorYellow
            setBackground(ivYellowShape)
            ivYellowShape.setBackgroundResource(R.drawable.yellow_shape_outlined)
            ivRedShape.setBackgroundResource(R.drawable.red_shape)
            ivBlueShape.setBackgroundResource(R.drawable.blue_shape)
            ivGreenShape.setBackgroundResource(R.drawable.green_shape)
            ivWhiteShape.setBackgroundResource(R.drawable.white_shape)
            ivPinkShape.setBackgroundResource(R.drawable.pink_shape)
            ivOrangeShape.setBackgroundResource(R.drawable.orange_shape)
        }
        ivOrangeShape.setOnClickListener {
            colorNote = R.attr.colorOrange
            setBackground(ivOrangeShape)
            ivOrangeShape.setBackgroundResource(R.drawable.orange_shape_outlined)
            ivRedShape.setBackgroundResource(R.drawable.red_shape)
            ivBlueShape.setBackgroundResource(R.drawable.blue_shape)
            ivGreenShape.setBackgroundResource(R.drawable.green_shape)
            ivPinkShape.setBackgroundResource(R.drawable.pink_shape)
            ivYellowShape.setBackgroundResource(R.drawable.yellow_shape)
            ivWhiteShape.setBackgroundResource(R.drawable.white_shape)
        }

        toolbar.apply {
            setActionIcon(R.drawable.ic_save)
            setActionListener {
                viewModel.addNote(
                    title = etAddTitle.text.toString(),
                    content = etAddContent.text.toString(),
                    color = colorNote
                )

                collectWithLifecycle(viewModel.message) { message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setBackground(imageView: ImageView) {
        binding.root.setBackgroundColor(
            requireContext().getColorFromAttr(
                when (imageView.id) {
                    R.id.ivWhiteShape -> R.attr.colorWhite
                    R.id.ivRedShape -> R.attr.colorRed
                    R.id.ivBlueShape -> R.attr.colorBlue
                    R.id.ivGreenShape -> R.attr.colorGreen
                    R.id.ivPinkShape -> R.attr.colorPink
                    R.id.ivYellowShape -> R.attr.colorYellow
                    R.id.ivOrangeShape -> R.attr.colorOrange
                    else -> R.attr.colorBlack
                }
            )
        )
    }
}