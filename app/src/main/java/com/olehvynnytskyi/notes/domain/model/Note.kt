package com.olehvynnytskyi.notes.domain.model

import android.os.Parcelable
import androidx.annotation.AttrRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    @AttrRes val color: Int
) : Parcelable