package com.example.sisig.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "stock")
@TypeConverters(Converters::class)
data class Stock(
    @PrimaryKey(autoGenerate = true)
    var stockId: Int = 0,  // Make stockId mutable (var instead of val)
    var totalStock: Int = 60,
    var updatedAt: Long = System.currentTimeMillis() // Store timestamp as Long
)



