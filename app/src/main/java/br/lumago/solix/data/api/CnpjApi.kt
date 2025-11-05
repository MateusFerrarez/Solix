package br.lumago.solix.data.api

import br.lumago.solix.data.dao.CnpjApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://publica.cnpj.ws/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()


object CnpjApi {
    val retrofitService: CnpjApiService by lazy {
        retrofit.create(CnpjApiService::class.java)
    }
}