package br.lumago.solix.data.repositories

import android.content.Context
import br.lumago.solix.data.database.AppDatabase
import br.lumago.solix.data.entities.Products

class CounterRepository (context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val paymentsDao = db.productDao()

    suspend fun insertPayment(products: Products){
        paymentsDao.insertProduct(products)
    }
}