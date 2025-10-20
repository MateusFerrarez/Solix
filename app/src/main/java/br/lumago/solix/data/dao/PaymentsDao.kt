package br.lumago.solix.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.lumago.solix.data.entities.Payments
import br.lumago.solix.data.entities.relations.PaymentCard

@Dao
interface PaymentsDao {

    @Insert
    suspend fun insertPayment(payment: Payments)

    // Get
    @Query(
        """SELECT PAY.PAYMENT_ID as paymentId, C.razao_social as customer, 
        PAY.month_value as value, PAY.contract_date contractDate, CASE WHEN PAY.synchronized_at IS NULL THEN 0 ELSE 1 END as isSychronized
        FROM PAYMENTS PAY
        INNER JOIN CUSTOMERS C ON PAY.customer_id = C.customer_id
        ORDER BY PAY.PAYMENT_ID DESC
    """
    )
    suspend fun getPayments(): List<PaymentCard>

    @Query("""SELECT * FROM PAYMENTS WHERE PAYMENT_ID = :paymentId""")
    suspend fun getPaymentById(paymentId: Long): Payments

    // Update
    @Update
    suspend fun updatePaymentByPayment(payment: Payments)

    // Delete
    @Query("""DELETE from PAYMENTS WHERE PAYMENT_ID = :paymentId""")
    suspend fun deletePaymentById(paymentId: Long)
}