package com.example.sisig.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert
    fun insert(stock: Stock)

    @Query("SELECT * FROM stock WHERE stockId = :id")
    fun getById(id: Int): Stock?

    @Query("SELECT * FROM stock")
    fun getAll(): List<Stock>

    @Query("UPDATE stock SET totalStock = :newStock WHERE stockId = :id")
    fun updateStock(id: Int, newStock: Int): Int

    @Delete
    fun delete(stock: Stock)

    @Query("DELETE FROM stock")
    fun deleteAll()

}
