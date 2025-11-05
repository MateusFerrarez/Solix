package br.lumago.solix.data.dao

import br.lumago.solix.data.entities.CustomerResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface CnpjApiService {

    @GET("cnpj/{cnpj}")
    suspend fun getCustomerByCnpj(@Path("cnpj") cnpj: String) : CustomerResponse

}