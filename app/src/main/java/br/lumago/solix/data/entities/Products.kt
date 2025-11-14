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
    @ColumnInfo(name = "product_id") @PrimaryKey(autoGenerate = true) var productId: Long = 0,
    @ColumnInfo(name = "enterprise_id") val enterpriseId: Long = 1L,
    @ColumnInfo(name = "partner_id") var partnerId: Long = 1L,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "barcode") var barcode: String = "",
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP") val createdAt: String? = null,
)
