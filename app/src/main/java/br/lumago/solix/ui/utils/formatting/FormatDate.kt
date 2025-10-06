package br.lumago.solix.ui.utils.formatting

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class FormatDate(private val date: String) {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US)
    private val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)

    fun format() : String {
        val date = LocalDate.parse(date, inputFormatter)
        val output = date.format(dateFormatter)
        return output
    }
}