package br.lumago.solix.data.api.models

import br.lumago.solix.data.api.models.Estabelecimento
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerResponse(
    @SerializedName("razao_social")
    val razaoSocial: String,
    @SerializedName("estabelecimento")
    val estabelecimento: Estabelecimento
)