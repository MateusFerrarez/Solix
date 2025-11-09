package br.lumago.solix.data.repositories

import android.content.Context
import br.lumago.solix.data.database.AppDatabase

class ProductsRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)

    private val productsDao = db.ProductsDao()

    suspend fun selectProduct(barcode: String): String?{
        return productsDao.getProductByBarcode(barcode)
    }


}