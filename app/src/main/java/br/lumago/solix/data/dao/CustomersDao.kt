package br.lumago.solix.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.lumago.solix.data.entities.relations.CustomerSelected

@Dao
interface CustomersDao {

    // Get
    @Query("""SELECT CS.CUSTOMER_ID as customerId, CS.razao_social as customer
        FROM CUSTOMERS CS
        INNER JOIN PAYMENTS PAY ON CS.CUSTOMER_ID = PAY.CUSTOMER_ID
        WHERE PAY.PAYMENT_ID = :paymentId
    """)
    suspend fun getCustomerSelectedByPaymentId(paymentId: Long) : CustomerSelected

    @Query("""SELECT CS.CUSTOMER_ID as customerId, CS.razao_social as customer
        FROM CUSTOMERS CS
        INNER JOIN PAYMENTS PAY ON CS.CUSTOMER_ID = PAY.INDICATOR_ID
        WHERE PAY.PAYMENT_ID = :paymentId
    """)
    suspend fun getIndicatorSelectedByPaymentId(paymentId: Long) : CustomerSelected?
}