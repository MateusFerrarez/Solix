package br.lumago.solix.data.repositories

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.lumago.solix.data.DelayedPaymentsPagingSource
import br.lumago.solix.data.database.AppDatabase
import br.lumago.solix.data.entities.Payments
import br.lumago.solix.data.entities.relations.CustomerSelected
import br.lumago.solix.data.entities.relations.PaymentCard
import kotlinx.coroutines.flow.Flow

class PaymentsRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val paymentsDao = db.paymentsDao()
    private val customerDao = db.customersDao()

    // Insert
    suspend fun insertPayment(payment: Payments) {
        paymentsDao.insertPayment(payment)
    }

    // Get
    fun getPayments(): Flow<PagingData<PaymentCard>> = Pager(
        config = PagingConfig(
            pageSize = 15,
            initialLoadSize = 15,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
    ) {
        DelayedPaymentsPagingSource(
            delegate = paymentsDao.getPayments(),
        )
    }.flow

    suspend fun getPaymentById(paymentId: Long): Payments {
        return paymentsDao.getPaymentById(paymentId)
    }

    suspend fun getCustomerSelectedByPaymentId(paymentId: Long): CustomerSelected {
        return customerDao.getCustomerSelectedByPaymentId(paymentId)
    }

    suspend fun getIndicatorSelectedByPaymentId(paymentId: Long): CustomerSelected? {
        return customerDao.getIndicatorSelectedByPaymentId(paymentId)
    }

    // Update
    suspend fun updatePaymentByPayment(payment: Payments) {
        paymentsDao.updatePaymentByPayment(payment)
    }

    // Delete
    suspend fun deletePaymentById(paymentId: Long) {
        paymentsDao.deletePaymentById(paymentId)
    }
}