package br.lumago.solix.exceptions.handler

import android.content.Context
import br.lumago.solix.exceptions.payment.DeletePaymentException
import br.lumago.solix.exceptions.payment.GetPaymentException
import br.lumago.solix.exceptions.payment.PaymentSynchronizedException
import br.lumago.solix.ui.utils.LogManager

class PaymentExceptionHandler(override val exception: Exception) : ExceptionHandler() {
    override fun formatException(): String {
        val formattedException = when(exception){
            is GetPaymentException -> "Erro ao listar mensalidades!"
            is DeletePaymentException -> "Erro ao deletar mensalidade!"
            is PaymentSynchronizedException -> exception.message!!
            else -> "Erro desconhecido"
        }

        return formattedException
    }

    override fun saveLog(context: Context) {
        LogManager(context).createLog(exception)
    }
}