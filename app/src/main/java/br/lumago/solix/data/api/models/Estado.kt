package br.lumago.solix.data.api.models

import com.google.gson.annotations.SerializedName

data class Estado(
    @SerializedName("sigla")
    val sigla: String
)
