package br.lumago.solix.ui.utils.components

import android.app.Activity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.lumago.solix.ui.theme.estiloFieldBusca

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultSearchBar(
    busca: String,
    onChange: (String) -> Unit,
    activity: Activity
) {

    val colors1 = SearchBarDefaults.colors(
        containerColor = Color.White
    )

    SearchBar(
        inputField = {
            TextField(
                colors = estiloFieldBusca(),
                modifier = Modifier.fillMaxWidth(),
                shape = ShapeDefaults.Large,
                value = busca,
                onValueChange = { onChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Icone de pesquisa",
                        tint = Color.Black
                    )
                },
                trailingIcon = {
                    Row {
                        IconButton(
                            onClick = {
                                onChange("")
                                activity.currentFocus?.clearFocus()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Icone de filtro",
                                modifier = Modifier
                                    .size(20.dp),
                            )
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Characters
                ),
            )
        },
        expanded = false,
        onExpandedChange = {},
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        content = { },
        colors = colors1
    )

}