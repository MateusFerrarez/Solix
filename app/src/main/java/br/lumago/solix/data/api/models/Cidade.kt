package br.lumago.solix.data.api.models

import com.google.gson.annotations.SerializedName

data class Cidade(
    @SerializedName("nome")
    val nome: String
)
