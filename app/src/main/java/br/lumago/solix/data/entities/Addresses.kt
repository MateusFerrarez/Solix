package br.lumago.solix.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Addresses",
    foreignKeys = [
        ForeignKey(
            entity = Customers::class,
            parentColumns = ["customer_id"],
            childColumns = ["customer_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Addresses(
    @ColumnInfo(name = "address_id") @PrimaryKey(autoGenerate = true) val addressId: Long = 0,
    @ColumnInfo(name = "customer_id") var customerId: Long = 0L,
    @ColumnInfo(name = "street") var street: String = "",
    @ColumnInfo(name = "neighborhood") var neighborhood: String = "",
    @ColumnInfo(name = "number") var number: String = "",
    @ColumnInfo(name = "reference") var reference: String = "",
    @ColumnInfo(name = "complement") var complement: String = "",
    @ColumnInfo(name = "zip_code") var zipCode: String = "",
    @ColumnInfo(name = "city") var city: String = "",
    @ColumnInfo(name = "state") var state: String = "",
    @ColumnInfo(name = "number_1") var number1: String = "",
    @ColumnInfo(name = "number_2") var number2: String = "",
    @ColumnInfo(name = "email_1") var email1: String = "",
    @ColumnInfo(name = "email_2") var email2: String = "",
    @ColumnInfo(name = "latitude") val latitude: Float = 0f,
    @ColumnInfo(name = "longitude") val longitude: Float = 0f,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP") val createdAt: String? = null,
)
