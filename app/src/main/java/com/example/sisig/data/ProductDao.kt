package com.example.sisig.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert
    fun insert(product: Product)

    @Insert
    fun insertAll(products: List<Product>)

    @Query("SELECT * FROM product WHERE productId = :id")
    fun getById(id: String): Flow<Product>

    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM product")
    fun deleteAll()

}


