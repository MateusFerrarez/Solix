package br.lumago.solix.data.adapters

import br.lumago.solix.data.entities.Addresses
import org.json.JSONObject

class AddressFirebaseAdapter : JsonAdapter {
    override fun <T> convertFromJson(
        json: JSONObject,
        targetClass: Class<T>
    ): T {
        if (targetClass == Addresses::class.java) {
            val bairro = json.getString("bairro")
            val cep = json.getString("cep")
            val cidade = json.getString("cidade")
            val complemento = json.getString("complemento")
            val logradouro = json.getString("logradouro")
            val numero = json.getString("numero")
            val uf = json.getString("uf")

            val address = Addresses().apply {
                this.neighborhood = bairro
                this.zipCode = cep
                this.city = cidade
                this.complement = complemento
                this.street = logradouro
                this.number = numero
                this.state = uf
            }

            @Suppress("UNCHECKED_CAST")
            return address as T
        }

        throw IllegalArgumentException("Unsupported type: ${targetClass.simpleName}")
    }
}

inline fun <reified T> AddressFirebaseAdapter.fromJson(json: JSONObject): T {
    return convertFromJson(json, T::class.java)
}