package br.lumago.solix.exceptions.handler

import android.content.Context
import br.lumago.solix.exceptions.paymentHandler.EmptyCustomerException
import br.lumago.solix.exceptions.paymentHandler.InvalidDateException
import br.lumago.solix.exceptions.paymentHandler.NewPaymentGetException
import br.lumago.solix.exceptions.paymentHandler.NewPaymentInsertException
import br.lumago.solix.exceptions.paymentHandler.NewPaymentUpdateException
import br.lumago.solix.ui.utils.LogManager

class PaymentManipulatorExceptionHandler(override val exception: Exception) : ExceptionHandler() {
    override fun formatException(): String {
        val formattedException = when(exception){
            is InvalidDateException, is EmptyCustomerException -> exception.message!!
            is NewPaymentInsertException -> "Erro ao salvar mensalidade!"
            is NewPaymentGetException -> "Erro ao carregar informações da mensalidade!"
            is NewPaymentUpdateException -> "Erro ao atualizar mensalidade!"
            else -> "Erro desconhecido"
        }

        return formattedException
    }

    override fun saveLog(context: Context) {
        LogManager(context).createLog(exception)
    }
}