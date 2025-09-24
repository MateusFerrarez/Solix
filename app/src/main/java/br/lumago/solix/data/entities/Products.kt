package br.lumago.solix.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Products",
    foreignKeys = [
        ForeignKey(
            entity = Enterprises::class,
            parentColumns = ["enterprise_id"],
            childColumns = ["enterprise_id"]
        )
    ],
    indices = [
        Index(
            unique = true,
            name = "IDX_PRODUCTS",
            value = ["enterprise_id", "partner_id"]
        )
    ]
)
data class Products(
    @ColumnInfo(name = "product_id") @PrimaryKey(autoGenerate = true) val productId: Long = 0,
    @ColumnInfo(name = "enterprise_id") val enterpriseId: Long,
    @ColumnInfo(name = "partner_id") val partnerId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "barcode") val barcode: String,
    @ColumnInfo(name = "created_at") val createdAt: String
)
