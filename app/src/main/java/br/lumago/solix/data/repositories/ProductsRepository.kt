package br.lumago.solix.data.repositories

import android.content.Context
import br.lumago.solix.data.database.AppDatabase
import br.lumago.solix.data.entities.Products
import br.lumago.solix.data.entities.relations.ItemCard

class ProductsRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val productDao = db.productsDao()

    // Insert
    suspend fun insertProducts(products: Products) {
        productDao.insertProducts(products)
    }

    // Get
    suspend fun getItemCardByBarcode(barcode: String): ItemCard? {
        return productDao.getItemCardByBarcode(barcode)
    }

    suspend fun getProductIdByBarcode(partnerId: Long): Long? {
        return productDao.getProductId(partnerId)
    }

    // Update
    suspend fun updateProducts(products: Products) {
        productDao.updateProducts(products)
    }
}