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
    @ColumnInfo(name = "customer_id") @PrimaryKey(autoGenerate = true) var customerId: Long = 0,
    @ColumnInfo(name = "enterprise_id") val enterpriseId: Long = 1L,
    @ColumnInfo(name = "partner_id") var partnerId: String? = null,
    @ColumnInfo(name = "firebase_id") val firebaseId: String? = null,
    @ColumnInfo(name = "razao_social") var razaoSocial: String = "",
    @ColumnInfo(name = "nome_fantasia") var nomeFantasia: String? = null,
    @ColumnInfo(name = "documento_1") var documento1: String = "",
    @ColumnInfo(name = "documento_2") var documento2: String? = null,
    @ColumnInfo(name = "status", defaultValue = "O") val status: String = "O",
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP") val createdAt: String? = null,
    @ColumnInfo(name = "updated_at") val updatedAt: String? = null,
    @ColumnInfo(name = "synchronized_at") var synchronizedAt: String? = null
)
