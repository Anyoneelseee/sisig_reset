package com.example.sisig.data

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithItems(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "uniqueOrderNo",
        entityColumn = "uniqueOrderNo"
    )
    val items: List<OrderItem>
)
