package br.lumago.solix.data.dao

import androidx.room.Dao
import androidx.room.Insert
import br.lumago.solix.data.entities.Products

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(products: Products)

    // Get

    // Update

    // Delete
}