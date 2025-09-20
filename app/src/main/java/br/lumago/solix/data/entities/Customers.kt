package br.lumago.solix.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Customers",
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
            name = "IDX_CUSTOMERS",
            value = ["enterprise_id", "partner_id"]
        )
    ]
)
data class Customers(
    @ColumnInfo(name = "customer_id") @PrimaryKey(autoGenerate = true) val customerId: Long = 0,
    @ColumnInfo(name = "enterprise_id") val enterpriseId: Long,
    @ColumnInfo(name = "partner_id") val partnerId: String? = null,
    @ColumnInfo(name = "firebase_id") val firebaseId: String? = null,
    @ColumnInfo(name = "razao_social") val razaoSocial: String,
    @ColumnInfo(name = "nome_fantasia") val nomeFantasia: String,
    @ColumnInfo(name = "documento_1") val documento1: String,
    @ColumnInfo(name = "documento_2") val documento2: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String? = null,
    @ColumnInfo(name = "synchronized_at") val synchronizedAt: String? = null
)
