package br.lumago.solix.exceptions.handler

import android.content.Context
import br.lumago.solix.exceptions.payment.PaymentDeleteException
import br.lumago.solix.exceptions.payment.PaymentGetException
import br.lumago.solix.exceptions.payment.PaymentSynchronizedException
import br.lumago.solix.ui.utils.LogManager

class PaymentHandler(override val exception: Exception) : ExceptionHandler() {
    override fun formatException(): String {
        val formattedException = when(exception){
            is PaymentGetException -> "Erro ao listar mensalidades!"
            is PaymentDeleteException -> "Erro ao deletar mensalidade!"
            is PaymentSynchronizedException -> exception.message!!
            else -> "Erro desconhecido"
        }

        return formattedException
    }

    override fun saveLog(context: Context) {
        LogManager(context).createLog(exception)
    }
}