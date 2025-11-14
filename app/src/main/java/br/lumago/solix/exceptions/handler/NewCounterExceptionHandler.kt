package br.lumago.solix.exceptions.handler

import android.content.Context
import br.lumago.solix.exceptions.newCounter.ProductNotFoundException
import br.lumago.solix.ui.utils.LogManager

class NewCounterExceptionHandler(override val exception: Exception) : ExceptionHandler() {
    override fun formatException(): String {
        val formattedException = when (exception) {
            is ProductNotFoundException -> exception.message!!
            else -> "Erro desconhecido"
        }

        return formattedException
    }

    override fun saveLog(context: Context) {
        LogManager(context).createLog(exception)
    }
}