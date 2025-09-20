package br.lumago.solix.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Addressses",
    foreignKeys = [
        ForeignKey(
            entity = Customers::class,
            parentColumns = ["customer_id"],
            childColumns = ["customer_id"]
        )
    ]
)
data class Addresses(
    @ColumnInfo(name = "address_id") @PrimaryKey(autoGenerate = true) val addressId: Long = 0,
    @ColumnInfo(name = "customer_id") val customerId: Long,
    @ColumnInfo(name = "street") val street: String,
    @ColumnInfo(name = "neighborhood") val neighborhood: String,
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "reference") val reference: String,
    @ColumnInfo(name = "complement") val complement: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "number_1") val number1: String,
    @ColumnInfo(name = "number_2") val number2: String,
    @ColumnInfo(name = "email_1") val email1: String,
    @ColumnInfo(name = "email_2") val email2: String,
    @ColumnInfo(name = "latitude") val latitude: Float,
    @ColumnInfo(name = "longitude") val longitude: Float,
    @ColumnInfo(name = "created_at") val createdAt: String
)
