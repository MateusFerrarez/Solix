package br.lumago.solix.ui.utils.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.ui.theme.estiloField
import br.lumago.solix.ui.theme.normalStyle
import br.lumago.solix.ui.theme.titleStyle
import br.lumago.solix.ui.utils.formatting.FormatCurrency

@Composable
fun TextWithTextField(
    text: String,
    value: TextFieldValue,
    minHeight: Dp = 50.dp,
    charLimit: Int = 500,
    onlyNumbers: Boolean = false,
    onValueChange: (TextFieldValue) -> Unit,
    widthPercentage: Float,
    roundedPercentage: Int = 60
) {
    Column {
        Text(
            text = text,
            fontSize = 15.sp,
            style = titleStyle,
            color = Color.Black
        )

        TextField(
            supportingText = {
                if (!onlyNumbers) {
                    Text(
                        text = "${value.text.length}/$charLimit"
                    )
                }
            },
            value = value,
            onValueChange = { newValue ->
                val formatedValue = when (onlyNumbers) {
                    true -> newValue.text.filter { text -> text.isDigit() }
                    false -> newValue.text.take(charLimit)
                }

                val tempNewValue = when (onlyNumbers) {
                    true -> FormatCurrency(formatedValue).formart()
                    false -> formatedValue
                }

                val tempFieldValue =
                    TextFieldValue(tempNewValue, selection = TextRange(tempNewValue.length))

                onValueChange(tempFieldValue)
            },
            modifier = Modifier
                .heightIn(min = minHeight)
                .fillMaxWidth(widthPercentage),
            keyboardOptions = KeyboardOptions(
                keyboardType = when (onlyNumbers) {
                    true -> KeyboardType.Number
                    false -> KeyboardType.Text
                },
                capitalization = KeyboardCapitalization.Characters
            ),
            colors = estiloField(),
            shape = RoundedCornerShape(roundedPercentage),
            textStyle = normalStyle,
            singleLine = onlyNumbers
        )
    }
}