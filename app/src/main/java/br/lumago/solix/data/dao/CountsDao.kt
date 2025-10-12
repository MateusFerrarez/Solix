package br.lumago.solix.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import br.lumago.solix.data.entities.Counts
import br.lumago.solix.data.entities.Items

@Dao
interface CountsDao {

    @Insert
    suspend fun insert (counts: Counts) : Long

    @Insert
    suspend fun insertItems (items: List<Items>)

    @Transaction
    suspend fun insertCountAndItems (counts: Counts, items: List<Items>){
        val id = insert(counts)
        items.forEach { items -> items.itemId = id }
        insertItems(items)
    }
}