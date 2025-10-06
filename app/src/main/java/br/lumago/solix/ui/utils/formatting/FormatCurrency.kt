package br.lumago.solix.ui.utils.formatting

import java.text.NumberFormat
import java.util.Locale

class FormatCurrency(private val initValue: String) {
    private val formatter = NumberFormat
        .getCurrencyInstance(Locale("pt-br", "br"))

    fun formart(): String {
        val valor = initValue.toBigDecimal().movePointLeft(2)
        return formatter.format(valor)
    }
}