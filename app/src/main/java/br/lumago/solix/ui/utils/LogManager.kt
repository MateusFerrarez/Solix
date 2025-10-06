package br.lumago.solix.ui.utils

import android.content.Context
import java.io.File
import java.time.LocalDateTime

class LogManager(
    private val context: Context
) {
    private val logFile =
        File(context.filesDir, "Log")

    fun createLog(exception: Exception) {
        if (logFile.exists().not()) {
            logFile.createNewFile()
        }

        val formattedText =
            "------\n" +
            "DATE: ${LocalDateTime.now()} - EXCEPTION CLASS - ${exception.javaClass.simpleName} - EXCEPTION MESSAGE - ${exception.message ?: "NULL MESSAGE"} - \nSTACKTRACE- ${exception.stackTraceToString()}" +
            "\n------"

        logFile.appendText(formattedText)
    }

}