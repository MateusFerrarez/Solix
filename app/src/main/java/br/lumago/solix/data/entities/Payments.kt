package br.lumago.solix.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Payments",
    foreignKeys = [
        ForeignKey(
            entity = Enterprises::class,
            parentColumns = ["enterprise_id"],
            childColumns = ["enterprise_id"]
        ),
        ForeignKey(
            entity = Customers::class,
            parentColumns = ["customer_id"],
            childColumns = ["customer_id"]
        ),
        ForeignKey(
            entity = Customers::class,
            parentColumns = ["customer_id"],
            childColumns = ["indicator_id"]
        )
    ]
)
data class Payments(
    @ColumnInfo(name = "payment_id") @PrimaryKey(autoGenerate = true) val paymentId: Long = 0,
    @ColumnInfo(name = "enterprise_id") val enterpriseId: Long,
    @ColumnInfo(name = "customer_id") var customerId: Long,
    @ColumnInfo(name = "indicator_id") var indicatorId: Long? = null,
    @ColumnInfo(name = "month_value") var monthValue: Double,
    @ColumnInfo(name = "due_date") var dueDate: String,
    @ColumnInfo(name = "contract_date") var contractDate: String,
    @ColumnInfo(name = "observation") var observation: String? = null,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") var updatedAt: String? = null,
    @ColumnInfo(name = "synchronized_at") val synchronizedAt: String? = null
)