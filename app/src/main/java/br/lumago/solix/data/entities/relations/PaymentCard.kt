package br.lumago.solix.data.entities.relations

data class PaymentCard(
    val paymentId: Long,
    val customer: String,
    val value: Double,
    val contractDate: String,
    val isSychronized: Boolean
)