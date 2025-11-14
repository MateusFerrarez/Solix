package br.lumago.solix.data.repositories

import android.content.Context
import br.lumago.solix.data.database.AppDatabase
import br.lumago.solix.data.entities.Counts
import br.lumago.solix.data.entities.relations.CountCard
import br.lumago.solix.data.entities.relations.ItemCard

class CountRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val countDao = db.countDao()

    suspend fun insert(count: Counts, items: List<ItemCard>) {
        countDao.insertCountAndItems(
            count,
            items
        )
    }

    // Get
    suspend fun getCounts() : List<CountCard> {
        return countDao.getCounts()
    }
}