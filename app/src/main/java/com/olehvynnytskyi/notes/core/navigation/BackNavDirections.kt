package com.olehvynnytskyi.notes.core.navigation

import android.os.Bundle
import androidx.navigation.NavDirections
import com.olehvynnytskyi.notes.R

object BackNavDirections : NavDirections {
    override val actionId: Int = R.id.backNavDirection
    override val arguments: Bundle = Bundle()
}