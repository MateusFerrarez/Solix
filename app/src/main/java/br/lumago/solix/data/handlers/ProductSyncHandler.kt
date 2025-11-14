package br.lumago.solix.data.handlers

import android.util.Log
import androidx.compose.runtime.produceState
import br.lumago.solix.data.adapters.ProductFirebaseAdapter
import br.lumago.solix.data.adapters.fromJson
import br.lumago.solix.data.entities.Products
import br.lumago.solix.data.repositories.ProductsRepository
import br.lumago.solix.data.viewModels.sync.SyncStatus
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import java.util.Collections

class ProductSyncHandler(
    val repository: ProductsRepository,
    val currentSyncStatus: MutableStateFlow<SyncStatus>
) {

    suspend fun startSync() {
        val productJsonList = listOf(
            JSONObject(
                """
                {
                    "nome": "Notebook",
                    "id_parceiro": 2,
                    "codigo_barras": "1234567890234"
                }
            """.trimIndent()
            ),
            JSONObject(
                """
                {
                    "nome": "Pc",
                    "id_parceiro": 3,
                    "codigo_barras": "8949461894921"
                }
            """.trimIndent()
            )
        )

        val productAdapter = ProductFirebaseAdapter()
        val productsList = Collections.synchronizedList(mutableListOf<Products>())

        coroutineScope {
            val jobs = productJsonList.map { json ->
                async {
                    val exctractedProduct = productAdapter.fromJson<Products>(json)
                    val tempProductId = repository.getProductIdByBarcode(exctractedProduct.partnerId)
                    if (tempProductId != null) {
                        exctractedProduct.productId = tempProductId
                    }
                    productsList.add(exctractedProduct)
                }
            }

            jobs.awaitAll()

            val dbProcess = productsList.map {product ->
                async {
                    if (product.productId != 0L) {
                        repository.updateProducts(
                            product
                        )
                    } else {
                        repository.insertProducts(
                            product
                        )
                    }
                }
            }
            dbProcess.awaitAll()
        }
        currentSyncStatus.update { SyncStatus.Loading("Gravando registros dos produtos...") }
    }
}