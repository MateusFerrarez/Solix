package br.lumago.solix.data.repositories

import android.content.Context
import br.lumago.solix.data.database.AppDatabase
import br.lumago.solix.data.entities.Payments

class PaymentsRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val paymentsDao = db.paymentsDao()

    suspend fun insertPayment(payment: Payments){
        paymentsDao.insertPayment(payment)
    }
}