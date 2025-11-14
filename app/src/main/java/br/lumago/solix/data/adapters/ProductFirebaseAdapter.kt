package br.lumago.solix.data.adapters

import br.lumago.solix.data.entities.Customers
import br.lumago.solix.data.entities.Products
import org.json.JSONObject

class ProductFirebaseAdapter : JsonAdapter {
    override fun <T> convertFromJson(
        json: JSONObject,
        targetClass: Class<T>
    ): T {
        if (targetClass == Products::class.java) {
            val partnerId = json.getLong("id_parceiro")
            val name = json.getString("nome")
            val barcode = json.getString("codigo_barras")

            val product = Products().apply {
                this.partnerId = partnerId
                this.name = name
                this.barcode = barcode
            }

            @Suppress("UNCHECKED_CAST")
            return product as T
        }

        throw IllegalArgumentException("Unsupported type: ${targetClass.simpleName}")
    }
}

inline fun <reified T> ProductFirebaseAdapter.fromJson(json: JSONObject): T {
    return convertFromJson(json, T::class.java)
}