package br.lumago.solix

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.lumago.solix.data.adapters.AddressFirebaseAdapter
import br.lumago.solix.data.adapters.CustomerFirebaseAdapter
import br.lumago.solix.data.adapters.fromJson
import br.lumago.solix.data.entities.Addresses
import br.lumago.solix.data.entities.Customers
import org.json.JSONObject

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class JsonAdapterTest {

    /*
    Converte o json do Firebase na classe Customer
     */
    @Test
    fun customerFirebaseAdapterFromJson() {
        val json = JSONObject()
        json.accumulate("razao_social", "HBO")
        json.accumulate("cnpj_cpf", "1")
        json.accumulate("id_parceiro", "40")
        json.accumulate("fantasia", "FANTASIA")

        val compara = Customers(
            razaoSocial = "HBO",
            documento1 = "1",
            partnerId = "40",
            nomeFantasia = "FANTASIA"
        )

        val adapter = CustomerFirebaseAdapter()
        val finalCustomer : Customers = adapter.fromJson(json)
        assertEquals(finalCustomer, compara)
    }

    @Test
    fun addressFirebaseAdapterFromJson() {
        val json = JSONObject()
        json.accumulate("bairro", "BAIRRO 1")
        json.accumulate("cep", "133")
        json.accumulate("cidade", "CIDADE")
        json.accumulate("complemento", "COMP")
        json.accumulate("logradouro", "LOG")
        json.accumulate("numero", "NUM")
        json.accumulate("uf", "SP")

        val expectedValue = Addresses(
            neighborhood = "BAIRRO 1",
            zipCode = "133",
            city = "CIDADE",
            complement = "COMP",
            state = "SP",
            number = "NUM",
            street = "LOG"
        )

        val adapter = AddressFirebaseAdapter()
        val jsonValue : Addresses = adapter.fromJson(json = json)
        assertEquals(jsonValue, expectedValue)
    }
}