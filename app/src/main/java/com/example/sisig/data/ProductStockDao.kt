package com.example.sisig.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductStockDao {
    @Query("SELECT * FROM product_stock")
    fun getAllProductStock(): Flow<List<ProductStock>>

    @Query("SELECT * FROM product_stock WHERE productName = :name")
    fun getProductStockByName(name: String): ProductStock?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productStock: ProductStock)

    @Update
    fun update(productStock: ProductStock)

    @Delete
    fun delete(productStock: ProductStock)

    @Query("DELETE FROM product_stock")
    fun deleteAll()

}


