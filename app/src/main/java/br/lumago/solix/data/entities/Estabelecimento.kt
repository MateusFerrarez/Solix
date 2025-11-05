package br.lumago.solix.data.entities

import br.lumago.solix.data.api.models.Cidade
import br.lumago.solix.data.api.models.Estado
import br.lumago.solix.data.api.models.InscricaoEstadual
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Estabelecimento (
    @SerializedName("cnpj")
    val cnpj: String,
    @SerializedName("nome_fantasia")
    val nomeFantasia: String,
    @SerializedName("tipo_logradouro")
    val tipoLogradouro: String,
    @SerializedName("logradouro")
    val logradouro: String,
    @SerializedName("numero")
    val numero: String,
    @SerializedName("complemento")
    val complemento: String,
    @SerializedName("bairro")
    val bairro: String,
    @SerializedName("cep")
    val cep: String,
    @SerializedName("ddd1")
    val ddd1: String?,
    @SerializedName("telefone1")
    val telefone1: String?,
    @SerializedName("telefone2")
    val telefone2: String?,
    @SerializedName("email")
    val email: String,
    val estado: Estado,
    val cidade: Cidade,
    @SerializedName("inscricoes_estaduais")
    val inscricoesEstadual: List<InscricaoEstadual>
)