package br.lumago.solix.data.dao

import br.lumago.solix.data.api.models.CustomerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CnpjApiService {

    @GET("cnpj/{cnpj}")
    suspend fun getCustomerByCnpj(@Path("cnpj") cnpj: String): Response<CustomerResponse>

}