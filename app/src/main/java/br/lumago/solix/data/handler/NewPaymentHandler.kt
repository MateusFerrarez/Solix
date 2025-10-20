package br.lumago.solix.data.handler

import android.content.Context
import br.lumago.solix.exceptions.newPayment.NewPaymentGetException
import br.lumago.solix.exceptions.newPayment.NewPaymentInsertException
import br.lumago.solix.exceptions.newPayment.NewPaymentUpdateException
import br.lumago.solix.ui.utils.LogManager

class NewPaymentHandler(override val exception: Exception) : ExceptionHandler() {
    override fun formatException(): String {
        val formattedException = when(exception){
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