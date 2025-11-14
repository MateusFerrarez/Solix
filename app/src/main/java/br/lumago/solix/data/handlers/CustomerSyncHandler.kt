package br.lumago.solix.data.handlers

import android.util.Log
import br.lumago.solix.data.adapters.AddressFirebaseAdapter
import br.lumago.solix.data.adapters.CustomerFirebaseAdapter
import br.lumago.solix.data.adapters.fromJson
import br.lumago.solix.data.entities.Addresses
import br.lumago.solix.data.entities.Customers
import br.lumago.solix.data.repositories.CustomerRepository
import br.lumago.solix.data.viewModels.sync.SyncStatus
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import java.util.Collections

class CustomerSyncHandler(
    val repository: CustomerRepository,
    val currentSyncStatus: MutableStateFlow<SyncStatus>
) {
    suspend fun startSync(syncDate: String) {
        val customerJsonList = listOf(
            JSONObject(
                """
                {
                    "cnpj_cpf": "12345678900",
                    "id_parceiro": "1",
                    "razao_social": "Tech Solutions LTDA",
                    "fantasia": "TechSol",
                    "endereco" : {
                        "bairro": "SOA DOMINGOS 2",
                        "cep": "13734284",
                        "cidade": "MOCOCA",
                        "complemento": "CASA MARROM",
                        "logradouro" : "RUA DOS JOAO",
                        "numero" : "45",
                        "uf" : "SP",
                        "latitude" : -21.469016468693095,
                        "longitude" : -47.00480243859147
                    }
                }
            """.trimIndent()
            ),
            JSONObject(
                """
                {
                    "cnpj_cpf": "156",
                    "id_parceiro": "31",
                    "razao_social": "COCA COLA",
                    "fantasia": "COCA COLA 2",
                    "endereco" : {
                        "bairro": "SOA DOMINGOS",
                        "cep": "13734284",
                        "cidade": "SAO JOAO DA BOA VISTA",
                        "complemento": "CASA MARROM",
                        "logradouro" : "RUA DOS JOAO",
                        "numero" : "45",
                        "uf" : "SP",
                        "latitude" : "-21.468112779155753",
                        "longitude" : "-47.00457105244086"
                    }
                }
            """.trimIndent()
            )
        )

        val customerAdapter = CustomerFirebaseAdapter()
        val addressAdapter = AddressFirebaseAdapter()
        val customersList = Collections.synchronizedList(mutableListOf<Customers>())
        val addressesList = Collections.synchronizedList(mutableListOf<Addresses>())

        coroutineScope {
            val jobs = customerJsonList.map { json ->
                async {
                    val extractedCustomerJson = customerAdapter.fromJson<Customers>(json)
                    val extractedAddressJson = addressAdapter.fromJson<Addresses>(json)
                    extractedCustomerJson.synchronizedAt = syncDate

                    val tempCustomerId =
                        repository.getCustomerId(extractedCustomerJson.partnerId!!.toLong())

                    if (tempCustomerId != null) {
                        extractedCustomerJson.customerId = tempCustomerId
                        extractedAddressJson.customerId = tempCustomerId
                    }

                    customersList.add(extractedCustomerJson)
                    addressesList.add(extractedAddressJson)
                }
            }
            jobs.awaitAll()

            val dbProcess = customersList.mapIndexed { index,  customer ->
                async {
                    if (customer.customerId != 0L) {
                        repository.updateCustomerAndAddress(
                            customer,
                            addressesList[index]
                        )
                    } else {
                        repository.insertCustomerAndAddress(
                            customer,
                            addressesList[index]
                        )
                    }
                }
            }
            dbProcess.awaitAll()
        }

        currentSyncStatus.update { SyncStatus.Loading("Gravando registros dos clientes...") }
    }
}