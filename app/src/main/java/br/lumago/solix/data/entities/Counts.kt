package br.lumago.solix.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "Counts")
data class Counts(
    @ColumnInfo(name = "count_id") @PrimaryKey(autoGenerate = true) val countId: Long = 0,
    @ColumnInfo(name = "total_count") val totalCount: Int,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP") val createdAt: String? = LocalDateTime.now().toString(),
    @ColumnInfo(name = "updated_at") val updatedAt: String? = null,
    @ColumnInfo(name = "synchronized_at") val synchronizedAt: String? = null
)
