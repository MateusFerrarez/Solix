package br.lumago.solix.exceptions.handler

import android.content.Context

abstract class ExceptionHandler() {
    abstract val exception: Exception
    abstract fun formatException(): String
    abstract fun saveLog(context: Context)
}