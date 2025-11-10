package br.lumago.solix.ui.customerHandler.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.viewModels.customer.CustomerHandlerViewModel
import br.lumago.solix.ui.utils.buttons.DefaultButton
import br.lumago.solix.ui.utils.components.TextWithTextField

@Composable
fun AddressView(viewModel: CustomerHandlerViewModel) {
    // TextFields values
    val zipCodeValue = viewModel.zipCodeValue.collectAsState().value
    val cityValue = viewModel.cityValue.collectAsState().value
    val stateValue = viewModel.stateValue.collectAsState().value
    val streetValue = viewModel.streetValue.collectAsState().value
    val neighborhoodValue = viewModel.neighborhoodValue.collectAsState().value
    val numberValue = viewModel.numberValue.collectAsState().value
    val complementValue = viewModel.complementValue.collectAsState().value
    val referenceValue = viewModel.referenceValue.collectAsState().value

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(
                rememberScrollState(),
                true
            )
    ) {
        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithTextField(
                    text = "CEP:",
                    value = zipCodeValue,
                    charLimit = 8,
                    onlyNumbers = true,
                    onValueChange = { viewModel.updateZipCode(it) },
                    widthPercentage = 0.6f
                )

                Spacer(modifier = Modifier.width(5.dp))

                DefaultButton(
                    text = "Consultar",
                    onClick = {

                    },
                    isEnable = zipCodeValue.text.length == 8
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            TextWithTextField(
                text = "Cidade:",
                value = cityValue,
                charLimit = 60,
                onlyNumbers = false,
                onValueChange = { viewModel.updateCity(it) },
                widthPercentage = 1f
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextWithTextField(
                text = "UF:",
                value = stateValue,
                charLimit = 2,
                onlyNumbers = false,
                onValueChange = { viewModel.updateState(it) },
                widthPercentage = 0.2f,
                roundedPercentage = 30
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextWithTextField(
                text = "Logradouro:",
                value = streetValue,
                charLimit = 60,
                onlyNumbers = false,
                onValueChange = { viewModel.updateStreet(it) },
                widthPercentage = 1f
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextWithTextField(
                text = "Bairro:",
                value = neighborhoodValue,
                charLimit = 60,
                onlyNumbers = false,
                onValueChange = { viewModel.updateNeighborhood(it) },
                widthPercentage = 1f
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextWithTextField(
                text = "Número:",
                value = numberValue,
                charLimit = 10,
                onlyNumbers = false,
                onValueChange = { viewModel.updateNumber(it) },
                widthPercentage = 0.2f,
                roundedPercentage = 30
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextWithTextField(
                text = "Complemento:",
                value = complementValue,
                charLimit = 60,
                onlyNumbers = false,
                onValueChange = { viewModel.updateComplement(it) },
                widthPercentage = 1f,
                roundedPercentage = 30
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextWithTextField(
                text = "Referencia:",
                value = referenceValue,
                charLimit = 60,
                onlyNumbers = false,
                onValueChange = { viewModel.updateReference(it) },
                widthPercentage = 1f,
                roundedPercentage = 30
            )
        }
    }
}