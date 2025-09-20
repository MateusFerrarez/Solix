package br.lumago.solix.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Groups",
    indices = [
        Index(
            unique = true,
            name = "IDX_GROUPS",
            value = ["partner_id"]
        )
    ]
)
data class Groups(
    @ColumnInfo(name = "group_id") @PrimaryKey(autoGenerate = true) val groupId: Long = 0,
    @ColumnInfo(name = "partner_id") val partnerId: String,
    @ColumnInfo(name = "created_at") val createdAt: String
)