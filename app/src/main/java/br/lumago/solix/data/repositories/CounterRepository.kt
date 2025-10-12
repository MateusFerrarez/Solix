package br.lumago.solix.data.repositories

import android.content.Context
import br.lumago.solix.data.database.AppDatabase
import br.lumago.solix.data.entities.Counts
import br.lumago.solix.data.entities.Items
import br.lumago.solix.data.entities.Products

class CounterRepository (context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val countDao = db.CountsDao()

    suspend fun insertCountAndItem (counts: Counts, items: List<Items>){
        countDao.insertCountAndItems(counts, items)
    }
}