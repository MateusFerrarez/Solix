package br.lumago.solix.data.handlers

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
                    "fantasia": "TechSol"
                }
            """.trimIndent()
            ),
            JSONObject(
                """
                {
                    "cnpj_cpf": "156",
                    "id_parceiro": "31",
                    "razao_social": "COCA COLA",
                    "fantasia": "COCA COLA 2"
                }
            """.trimIndent()
            )
        )

        val addressJsonList = listOf(
            JSONObject(
                """
                {
                    "bairro": "SOA DOMINGOS",
                    "cep": "13733000",
                    "cidade": "MOCOCA",
                    "complemento": "CASA VERDE",
                    "logradouro" : "RUA DOS VIVOS",
                    "numero" : "4564",
                    "uf" : "SP"
                }
            """.trimIndent()
            ),
            JSONObject(
                """
                {
                    "bairro": "SOA DOMINGOS 2",
                    "cep": "13734284",
                    "cidade": "MOCOCA",
                    "complemento": "CASA MARROM",
                    "logradouro" : "RUA DOS JOAO",
                    "numero" : "45",
                    "uf" : "SP"
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
                    extractedCustomerJson.synchronizedAt = syncDate
                    customersList.add(extractedCustomerJson)
                }
            }

            jobs.awaitAll()

            val jobsAddress = addressJsonList.map { json ->
                async {
                    val extractedAddressJson = addressAdapter.fromJson<Addresses>(json)
                    addressesList.add(extractedAddressJson)
                }
            }

            jobsAddress.awaitAll()
        }

        currentSyncStatus.update { SyncStatus.Loading("Gravando registros dos clientes...") }

        repository.insertCustomersAndAddress(
            customersList,
            addressesList
        )
    }
}