package br.lumago.solix.exceptions.handler

import android.content.Context
import br.lumago.solix.exceptions.customer.GetCustomerException
import br.lumago.solix.ui.utils.LogManager
import java.lang.Exception

class CustomerExceptionHandler(override val exception: Exception) : ExceptionHandler() {
    override fun formatException(): String {
        val formattedException = when (exception) {
            is GetCustomerException -> "Erro ao listar clientes!"
            else -> "Erro desconhecido"
        }

        return formattedException
    }

    override fun saveLog(context: Context) {
        LogManager(context).createLog(exception)
    }
}