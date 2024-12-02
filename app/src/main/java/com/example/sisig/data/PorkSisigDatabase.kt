package com.example.sisig.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Order::class, OrderItem::class, Product::class, Stock::class, AllOrder::class, ProductStock::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val productStockDao: ProductStockDao
    abstract val orderDao: OrderDao
    abstract val orderItemDao: OrderItemDao
    abstract val productDao: ProductDao
    abstract val stockDao: StockDao
    abstract val allOrderDao: AllOrderDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS all_orders (
                        orderId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        orderDetail TEXT NOT NULL,
                        totalAmount REAL NOT NULL
                    )
                """)
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create temporary table with auto-increment
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS all_orders_temp (
                orderId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                orderDetail TEXT NOT NULL,
                totalAmount REAL NOT NULL
            )
        """)

                // Copy data from old table to new
                database.execSQL("""
            INSERT INTO all_orders_temp (orderDetail, totalAmount)
            SELECT orderDetail, totalAmount FROM all_orders
        """)

                // Drop old table
                database.execSQL("DROP TABLE all_orders")

                // Rename temp table to final table
                database.execSQL("ALTER TABLE all_orders_temp RENAME TO all_orders")
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS product_stock (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        productName TEXT NOT NULL,
                        quantity INTEGER NOT NULL
                    )
                """)
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "PorkSisigDatabase"
                )
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .addMigrations(MIGRATION_3_4)
                    .allowMainThreadQueries() // For testing only
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
