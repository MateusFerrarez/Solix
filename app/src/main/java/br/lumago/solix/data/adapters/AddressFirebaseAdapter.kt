package br.lumago.solix.data.adapters

import android.util.Log
import br.lumago.solix.data.entities.Addresses
import org.json.JSONObject

class AddressFirebaseAdapter : JsonAdapter {
    override fun <T> convertFromJson(
        json: JSONObject,
        targetClass: Class<T>
    ): T {
        if (targetClass == Addresses::class.java) {
            val obj = json.getJSONObject("endereco")
            val bairro = obj.getString("bairro")
            val cep = obj.getString("cep")
            val cidade = obj.getString("cidade")
            val complemento = obj.getString("complemento")
            val logradouro = obj.getString("logradouro")
            val numero = obj.getString("numero")
            val uf = obj.getString("uf")
            val latitude = obj.optString("latitude")
            val longitude = obj.optString("longitude")

            val address = Addresses().apply {
                this.neighborhood = bairro
                this.zipCode = cep
                this.city = cidade
                this.complement = complemento
                this.street = logradouro
                this.number = numero
                this.state = uf
                this.longitude = longitude
                this.latitude = latitude
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