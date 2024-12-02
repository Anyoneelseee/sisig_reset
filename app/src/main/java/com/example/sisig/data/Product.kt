package com.example.sisig.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey var productId: String,
    var productName: String,
    var productDescription: String? = null,
    var productPrice: Double
)

