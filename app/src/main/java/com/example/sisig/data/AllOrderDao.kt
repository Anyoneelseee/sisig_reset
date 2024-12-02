package com.example.sisig.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(Converters::class)
interface AllOrderDao {
    @Insert
    fun insert(allOrder: AllOrder): Long

    @Query("SELECT * FROM all_orders")
    fun getAllOrders(): Flow<List<AllOrder>>

    @Query("DELETE FROM all_orders")
    fun deleteAll()
}
