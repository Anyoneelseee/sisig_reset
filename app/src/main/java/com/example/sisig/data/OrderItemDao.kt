package com.example.sisig.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderItemDao {
    @Insert
    fun insert(orderItem: OrderItem)

    @Insert
    fun insertAll(orderItems: List<OrderItem>)

    @Query("SELECT * FROM order_items WHERE uniqueOrderNo = :orderNo")
    fun getByOrderId(orderNo: String): List<OrderItem>

    @Delete
    fun delete(orderItem: OrderItem)

    @Query("DELETE FROM order_items WHERE uniqueOrderNo = :orderNo")
    fun deleteByOrderId(orderNo: String): Int

    @Query("DELETE FROM order_items")
    fun deleteAll()

}



