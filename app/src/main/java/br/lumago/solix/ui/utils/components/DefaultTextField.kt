package br.lumago.solix.ui.utils.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.lumago.solix.ui.theme.estiloField

@Composable
fun DefaultTextField(
    label: String,
    field: TextFieldValue,
    onTextFieldChange: (TextFieldValue) -> Unit,
    onIconClick: () -> Unit,
    icon: ImageVector,
    isClickable: Boolean = false,
    action: ImeAction = ImeAction.Next,
    isPassword: Boolean,
    showPassword: Boolean
) {
    TextField(
        singleLine = true,
        value = field,
        onValueChange = { newValue -> onTextFieldChange(newValue) },
        label = { Text(text = label) },
        leadingIcon = {
            if (isClickable) {
                IconButton(
                    onClick = {
                        onIconClick()
                    },
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }
        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = estiloField(),
        visualTransformation = when (showPassword) {
            true -> VisualTransformation.None
            else -> PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = when (isPassword) {
                true -> KeyboardType.Password
                false -> KeyboardType.Email
            },
            imeAction = action
        )
    )
}