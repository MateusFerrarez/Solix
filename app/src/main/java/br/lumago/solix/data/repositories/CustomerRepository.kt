package br.lumago.solix.data.repositories

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.lumago.solix.data.database.AppDatabase
import br.lumago.solix.data.entities.Addresses
import br.lumago.solix.data.entities.Customers
import br.lumago.solix.data.entities.relations.CustomerCard
import br.lumago.solix.data.paging.DelayedCustomersPagingSource
import kotlinx.coroutines.flow.Flow

class CustomerRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val customerDao = db.customersDao()

    suspend fun insertCustomersAndAddress(
        customers: List<Customers>,
        addresses: List<Addresses>
    ) {
        customerDao.insertCustomersAndAddress(
            customers,
            addresses
        )
    }

    suspend fun insertCustomerAndAddress(
        customer: Customers,
        address: Addresses
    ) {
        customerDao.insertCustomerAndAddress(
            customer,
            address
        )
    }

    // Get
    fun getCustomers(query: String): Flow<PagingData<CustomerCard>> = Pager(
        config = PagingConfig(
            pageSize = 15,
            initialLoadSize = 15,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
    ) {
        DelayedCustomersPagingSource(
            delegate = customerDao.getCustomers(query)
        )
    }.flow

    suspend fun getCustomerById(id: Long): Customers {
        return customerDao.getCustomerById(id)
    }

    suspend fun getAddressByCustomerId(id: Long): Addresses {
        return customerDao.getAddressByCustomerId(id)
    }

    suspend fun getNextCustomerId(): Long {
        return customerDao.getNextCustomerId()
    }

    // Update
    suspend fun updateCustomerAndAddress(
        customer: Customers,
        address: Addresses
    ) {
        customerDao.updateCustomerAndAddress(
            customer,
            address
        )
    }
}