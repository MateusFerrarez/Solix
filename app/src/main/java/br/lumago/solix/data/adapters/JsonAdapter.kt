package br.lumago.solix.data.adapters

import org.json.JSONObject

interface JsonAdapter {

    fun <T> convertFromJson(json: JSONObject, targetClass: Class<T>): T
}