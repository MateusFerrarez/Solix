package br.lumago.solix.ui.utils.formatting

class FormatString {
    companion object {
        fun removeAcento(string: String): String {
            val mapa = mapOf(
                "á" to "a",
                "é" to "e",
                "ã" to "a",
                "í" to "i",
                "ó" to "o",
                "õ" to "o",
                "ú" to "u",
                "ç" to "c",
                "à" to "a",
                "è" to "e",
                "ì" to "i",
                "ò" to "o",
                "ù" to "u",
            )

            val novaString = StringBuilder()
            novaString.append(string.lowercase())
            novaString.forEach {
                if (mapa.containsKey(it.toString())) {
                    novaString.replace(
                        novaString.indexOf(it.toString()),
                        novaString.indexOf(it.toString()) + 1,
                        mapa[it.toString()]!!
                    )
                }
            }
            return novaString.toString().uppercase()
        }
    }
}