package br.lumago.solix.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Enterprises",
    foreignKeys = [
        ForeignKey(
            entity = Groups::class,
            parentColumns = ["group_id"],
            childColumns = ["group_id"]
        )
    ],
    indices = [
        Index(
            unique = true,
            name = "IDX_ENTERPRISES",
            value = ["group_id", "partner_id"]
        )
    ]
)
data class Enterprises(
    @ColumnInfo(name = "enterprise_id") @PrimaryKey(true) val enterpriseId: Long = 0,
    @ColumnInfo(name = "group_id") val groupId: Long,
    @ColumnInfo(name = "partner_id") val partnerId: Long,
    @ColumnInfo(name = "created_at") val createdAt: String
)
