package br.lumago.solix.exceptions.handler

import android.content.Context
import br.lumago.solix.exceptions.customerHandler.CustomerUpdateException
import br.lumago.solix.exceptions.customerHandler.EmptyCustomerNameException
import br.lumago.solix.exceptions.customerHandler.NewCustomerInsertException
import br.lumago.solix.ui.utils.LogManager

class CustomerHandlerExceptionHandler(override val exception: Exception) : ExceptionHandler() {
    override fun formatException(): String {
        val formattedException = when(exception){
            is NewCustomerInsertException -> "Erro ao salvar cliente!"
            is CustomerUpdateException -> "Erro ao atualizar cliente!"
            is EmptyCustomerNameException -> exception.message!!
            else -> "Erro desconhecido"
        }

        return formattedException
    }

    override fun saveLog(context: Context) {
        LogManager(context).createLog(exception)
    }
}