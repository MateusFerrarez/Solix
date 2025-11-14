package br.lumago.solix.exceptions.handler

import android.content.Context
import br.lumago.solix.exceptions.count.CountException
import br.lumago.solix.ui.utils.LogManager

class CountExceptionHandler(override val exception: Exception) : ExceptionHandler() {
    override fun formatException(): String {
        val formattedException = when(exception){
            is CountException -> "Erro ao listar contagens"
            else -> "Erro desconhecido"
        }

        return formattedException    }

    override fun saveLog(context: Context) {
        LogManager(context).createLog(exception)
    }
}