package br.lumago.solix.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import br.lumago.solix.data.entities.Counts
import br.lumago.solix.data.entities.Items
import br.lumago.solix.data.entities.relations.CountCard
import br.lumago.solix.data.entities.relations.ItemCard

@Dao
interface CountDao {

    @Insert
    suspend fun insertCount(count: Counts) : Long

    @Insert
    suspend fun insertItems(items: List<Items>)

    @Transaction
    suspend fun insertCountAndItems(count: Counts, items: List<ItemCard>) {
        val countId = insertCount(count)
        val tempItems = List(items.size) {
            Items(
                countId = countId,
                productId = items[it].localId,
            )
        }

        insertItems(tempItems)
    }

    // Get
    @Query("""SELECT C.count_id as countId, C.total_count as countQuantity, C.created_at as date
        FROM Counts C
    """)
    suspend fun getCounts() : List<CountCard>
}