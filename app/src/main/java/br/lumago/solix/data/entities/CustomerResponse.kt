package br.lumago.solix.data.entities

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerResponse(
    @SerializedName("razao_social")
    val razaoSocial: String,
    @SerializedName("estabelecimento")
    val estabelecimento: Estabelecimento
)