package br.lumago.solix.exceptions.handler

import android.content.Context
import br.lumago.solix.exceptions.sync.SyncCustomerException
import br.lumago.solix.ui.utils.LogManager

class SyncExceptionHandler(override val exception: Exception) : ExceptionHandler() {
    override fun formatException(): String {
        val formattedException = when (exception) {
            is SyncCustomerException -> "Erro ao sincronizar clientes!"
            else -> "Erro desconhecido"
        }

        return formattedException
    }

    override fun saveLog(context: Context) {
        LogManager(context).createLog(exception)
    }
}