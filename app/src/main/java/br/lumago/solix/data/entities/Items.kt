package br.lumago.solix.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Items",
    foreignKeys = [
        ForeignKey(
            entity = Products::class,
            parentColumns = ["product_id"],
            childColumns = ["product_id"]
        ),
        ForeignKey(
            entity = Counts::class,
            parentColumns = ["count_id"],
            childColumns = ["count_id"]
        )
    ],
)
data class Items(
    @ColumnInfo(name = "item_id") @PrimaryKey(autoGenerate = true) var itemId: Long = 0,
    @ColumnInfo(name = "count_id") val countId: Long,
    @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "created_at") val createdAt: String? = null
)