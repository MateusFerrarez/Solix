package br.lumago.solix.ui.clientCadastroGeral.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CampoTexto(
    label: String,
    valor: String,
    tipoTeclado: KeyboardType = KeyboardType.Text,
    aoMudar: (String) -> Unit
) {
    OutlinedTextField(
        value = valor,
        onValueChange = aoMudar,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
        singleLine = true
    )
}
