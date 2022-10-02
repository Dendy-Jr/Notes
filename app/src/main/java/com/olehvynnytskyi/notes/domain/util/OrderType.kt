package com.olehvynnytskyi.notes.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}