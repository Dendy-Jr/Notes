package com.olehvynnytskyi.notes.data.storage

import com.olehvynnytskyi.notes.core.StorageProvider
import com.olehvynnytskyi.notes.domain.util.NoteOrder
import com.olehvynnytskyi.notes.domain.util.OrderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteOrderStorage @Inject constructor(
    storageProvider: StorageProvider,
    private val json: Json,
) {

    private val storage = storageProvider.getStorage(NOTE_ORDER_STORAGE)

    suspend fun updateOrder(
        order: NoteOrder
    ): Unit = withContext(Dispatchers.IO) {

        val raw = when (order) {
            is NoteOrder.Title -> {
                when (order.orderType) {
                    OrderType.Ascending -> {
                        json.encodeToString<NoteOrder>(NoteOrder.Title(OrderType.Ascending))
                    }
                    else -> {
                        json.encodeToString<NoteOrder>(NoteOrder.Title(OrderType.Descending))

                    }
                }
            }
            is NoteOrder.Date -> {
                when (order.orderType) {
                    OrderType.Ascending -> {
                        json.encodeToString<NoteOrder>(NoteOrder.Date(OrderType.Ascending))
                    }
                    else -> {
                        json.encodeToString<NoteOrder>(NoteOrder.Date(OrderType.Descending))

                    }
                }
            }
            is NoteOrder.Color -> {
                when (order.orderType) {
                    OrderType.Ascending -> {
                        json.encodeToString<NoteOrder>(NoteOrder.Color(OrderType.Ascending))
                    }
                    else -> {
                        json.encodeToString<NoteOrder>(NoteOrder.Color(OrderType.Descending))

                    }
                }
            }
        }

        storage.edit().putString(NOTE_ORDER_ITEM, raw).apply()
    }

    suspend fun getOrder(): NoteOrder? = withContext(Dispatchers.IO) {
        try {
            val raw = storage.getString(NOTE_ORDER_ITEM, "") ?: ""
            json.decodeFromString<NoteOrder>(raw)
        } catch (e: Exception) {
            null
        }
    }

    private companion object {
        const val NOTE_ORDER_STORAGE = "NOTE_ORDER_STORAGE"
        const val NOTE_ORDER_ITEM = "NOTE_ORDER_ITEM"
    }
}