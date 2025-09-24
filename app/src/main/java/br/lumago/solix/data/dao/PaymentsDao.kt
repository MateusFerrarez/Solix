package br.lumago.solix.data.dao

import androidx.room.Dao
import androidx.room.Insert
import br.lumago.solix.data.entities.Payments

@Dao
interface PaymentsDao {

    @Insert
    suspend fun insertPayment(payment: Payments)

    // Get

    // Update

    // Delete
}