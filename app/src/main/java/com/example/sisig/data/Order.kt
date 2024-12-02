package com.example.sisig.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey
    val uniqueOrderNo: String,
    val orderDate: String,
    val totalAmount: Double,
    val status: String = "Pending",
    val paymentMethod: String,
    val createdAt: String,
    val updatedAt: String
)





