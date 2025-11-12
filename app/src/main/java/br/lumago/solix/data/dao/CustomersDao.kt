package br.lumago.solix.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import br.lumago.solix.data.entities.Addresses
import br.lumago.solix.data.entities.Customers
import br.lumago.solix.data.entities.relations.CustomerCard
import br.lumago.solix.data.entities.relations.CustomerSelected

@Dao
interface CustomersDao {

    // Insert
    @Upsert()
    fun insertCustomers(customers: List<Customers>): List<Long>

    @Insert
    fun insertAddress(addresses: Addresses)

    @Insert
    fun insertCustomer(customer: Customers): Long

    @Transaction
    suspend fun insertCustomersAndAddress(
        customers: List<Customers>,
        addresses: List<Addresses>
    ) {
        val customersIds = insertCustomers(customers)
        deleteAllAddresses()

        customersIds.forEachIndexed { index, id ->
            val address = addresses[index]
            address.customerId = id
            insertAddress(address)
        }
    }

    @Transaction
    suspend fun insertCustomerAndAddress(
        customers: Customers,
        addresses: Addresses
    ) {
        val customerId = insertCustomer(customers)
        addresses.customerId = customerId
        insertAddress(addresses)
    }

    // Get
    @Query(
        """SELECT CS.CUSTOMER_ID as customerId, CS.razao_social as customer
        FROM CUSTOMERS CS
        INNER JOIN PAYMENTS PAY ON CS.CUSTOMER_ID = PAY.CUSTOMER_ID
        WHERE PAY.PAYMENT_ID = :paymentId
    """
    )
    suspend fun getCustomerSelectedByPaymentId(paymentId: Long): CustomerSelected

    @Query(
        """ SELECT C.CUSTOMER_ID as customerId, C.partner_id as partnerId, C.razao_social as customerName,
        ad.street || ', ' || ad.number || ' - ' ||ad.neighborhood as address,
        ad.city || ' - ' || ad.state as city, CASE WHEN (c.synchronized_at) IS NULL THEN 0 ELSE 1 END as isSync
        FROM CUSTOMERS C
        INNER JOIN addresses ad on ad.customer_id = C.customer_id
        WHERE (:query = '%%' OR C.razao_social LIKE :query OR C.nome_fantasia LIKE :query OR C.partner_id LIKE :query)
    """
    )
    fun getCustomers(query: String): PagingSource<Int, CustomerCard>

    @Query("""
        SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID = :customerId
    """)
    suspend fun getCustomerById(customerId: Long): Customers

    @Query("""
        SELECT * FROM ADDRESSES WHERE CUSTOMER_ID = :customerId
    """)
    suspend fun getAddressByCustomerId(customerId: Long): Addresses

    @Query(
        """SELECT CS.CUSTOMER_ID as customerId, CS.razao_social as customer
        FROM CUSTOMERS CS
        INNER JOIN PAYMENTS PAY ON CS.CUSTOMER_ID = PAY.INDICATOR_ID
        WHERE PAY.PAYMENT_ID = :paymentId
    """
    )
    suspend fun getIndicatorSelectedByPaymentId(paymentId: Long): CustomerSelected?

    @Query(
        """SELECT SEQ + 1
        FROM sqlite_sequence
        WHERE NAME = 'Customers'
        """
    )
    suspend fun getNextCustomerId(): Long

    // Update
    @Update
    suspend fun updateCustomer(customer: Customers)

    @Update
    suspend fun updateAddress(address: Addresses)

    @Transaction
    suspend fun updateCustomerAndAddress(
        customer: Customers,
        address: Addresses
    ){
        updateCustomer(customer)
        updateAddress(address)
    }

    // Delete
    @Query("""DELETE FROM ADDRESSES""")
    suspend fun deleteAllAddresses()

    @Query("""DELETE FROM Customers WHERE CUSTOMER_ID = :customerId""")
    suspend fun deleteCustomerById(customerId: Long)
}