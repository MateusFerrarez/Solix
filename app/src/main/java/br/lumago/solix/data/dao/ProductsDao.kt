package br.lumago.solix.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProductsDao {

    @Query("SELECT product_id || ' - ' || name AS 'product' FROM Products WHERE barcode = :barcode LIMIT 1")
    suspend fun getProductByBarcode(barcode: String): String?
}