package com.example.sisig.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_items",
    foreignKeys = [
        ForeignKey(
            entity = Order::class,
            parentColumns = ["uniqueOrderNo"],
            childColumns = ["uniqueOrderNo"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["uniqueOrderNo"]),
        Index(value = ["productId"])
    ]
)
data class OrderItem(
    @PrimaryKey(autoGenerate = true) val orderItemId: Int = 0,
    var uniqueOrderNo: String,
    var productId: String,
    var productName: String,
    var productPrice: Double,
    var quantity: Int,
    var totalPrice: Double
)


