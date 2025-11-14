package br.lumago.solix.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.lumago.solix.data.entities.Products
import br.lumago.solix.data.entities.relations.ItemCard

@Dao
interface ProductsDao {

    @Insert
    suspend fun insertProducts(product: Products)

    // Get
    @Query("""SELECT P.PRODUCT_ID as localId, P.partner_id || ' - ' || P.name as productName
        FROM PRODUCTS P
        WHERE P.barcode = :barcode
    """)
    suspend fun getItemCardByBarcode(barcode: String): ItemCard?

    @Query("""SELECT P.PRODUCT_ID
        FROM PRODUCTS P
        WHERE P.partner_id = :partnerId
        AND P.enterprise_id = 1
    """)
    suspend fun getProductId(partnerId: Long): Long?

    // Update
    @Update
    suspend fun updateProducts(product: Products)

}