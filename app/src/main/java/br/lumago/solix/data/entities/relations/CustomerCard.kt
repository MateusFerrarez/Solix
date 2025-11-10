package br.lumago.solix.data.entities.relations

data class CustomerCard (
    val customerId: Long,
    val partnerId: String,
    val customerName: String,
    val address: String,
    val city: String,
    val isSync: Boolean
)