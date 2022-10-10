package com.olehvynnytskyi.notes.domain.util

import kotlinx.serialization.Serializable

typealias DefaultType = OrderType.Descending

@Serializable
sealed class OrderType {

    @Serializable
    object Ascending : OrderType()

    @Serializable
    object Descending : OrderType()
}