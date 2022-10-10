package com.olehvynnytskyi.notes.domain.util

import kotlinx.serialization.Serializable

typealias DefaultOrder = NoteOrder.Date

@Serializable
sealed class NoteOrder {

    abstract val orderType: OrderType

    @Serializable
    class Title(override val orderType: OrderType) : NoteOrder()

    @Serializable
    class Date(override val orderType: OrderType) : NoteOrder()

    @Serializable
    class Color(override val orderType: OrderType) : NoteOrder()
}