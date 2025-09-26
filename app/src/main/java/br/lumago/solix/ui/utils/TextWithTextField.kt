package br.lumago.solix.ui.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.ui.theme.titleStyle

@Composable
fun TextWithTextField(
    text: String,
    value: TextFieldValue,
    onlyNumbers: Boolean = false,
    onValueChange: () -> Unit,
    heigth: Dp,
    widthPercentage: Float
) {
    Column {
        Text(
            text = text,
            fontSize = 14.sp,
            style = titleStyle
        )

        TextField(
            value = value,
            onValueChange = { onValueChange() },
            modifier = Modifier
                .fillMaxWidth(widthPercentage)
                .height(heigth),
            keyboardOptions = KeyboardOptions(
                keyboardType = when (onlyNumbers) {
                    true -> KeyboardType.Number
                    false -> KeyboardType.Text
                }
            ),

        )
    }
}