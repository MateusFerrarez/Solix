package br.lumago.solix.data.adapters

import br.lumago.solix.data.entities.Customers
import org.json.JSONObject

class CustomerFirebaseAdapter : JsonAdapter {
    override fun <T> convertFromJson(
        json: JSONObject,
        targetClass: Class<T>
    ): T {
        if (targetClass == Customers::class.java) {
            val documento1 = json.getString("cnpj_cpf")
            val partnerId = json.getString("id_parceiro")
            val razao = json.getString("razao_social")
            val fantasia = json.getString("fantasia")

            val customer = Customers().apply {
                this.documento1 = documento1
                this.partnerId = partnerId
                this.razaoSocial = razao
                this.nomeFantasia = fantasia
            }

            @Suppress("UNCHECKED_CAST")
            return customer as T
        }

        throw IllegalArgumentException("Unsupported type: ${targetClass.simpleName}")
    }
}

inline fun <reified T> CustomerFirebaseAdapter.fromJson(json: JSONObject): T {
    return convertFromJson(json, T::class.java)
}
