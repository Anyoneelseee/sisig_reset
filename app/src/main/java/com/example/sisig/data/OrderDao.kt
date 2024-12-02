package com.example.sisig.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders WHERE uniqueOrderNo = :orderNo")
    fun getById(orderNo: String): Flow<Order>

    @Insert
    fun insert(order: Order)

    @Query("DELETE FROM orders")
    fun deleteAll()
}








