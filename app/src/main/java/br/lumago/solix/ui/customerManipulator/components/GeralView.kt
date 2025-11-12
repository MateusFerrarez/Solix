package br.lumago.solix.ui.customerManipulator.components

import androidx.compose.foundation.background
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
fun GeralView(viewModel: CustomerHandlerViewModel) {
    // TextFields values
    val documento1Value = viewModel.documento1Value.collectAsState().value
    val documento2Value = viewModel.documento2Value.collectAsState().value
    val razaoSocialValue = viewModel.razaoSocialValue.collectAsState().value
    val nomeFantasiaValue = viewModel.nomeFantasiaValue.collectAsState().value

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
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithTextField(
                    text = "CNPJ/CPF:",
                    value = documento1Value,
                    charLimit = 14,
                    onlyNumbers = true,
                    onValueChange = { viewModel.updateDocumento1(it) },
                    widthPercentage = 0.6f
                )

                Spacer(modifier = Modifier.width(5.dp))

                DefaultButton(
                    text = "Consultar",
                    onClick = {
                        viewModel.checkCnpjApi()
                    },
                    isEnable = documento1Value.text.length == 14
                )
            }

            TextWithTextField(
                text = "Inscrição Estadual/RG",
                value = documento2Value,
                charLimit = 14,
                onlyNumbers = true,
                onValueChange = {
                    viewModel.updateDocumento2(it)
                },
                widthPercentage = 0.5f
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextWithTextField(
                text = "Nome/Razão social:",
                value = razaoSocialValue,
                charLimit = 60,
                onlyNumbers = false,
                onValueChange = {
                    viewModel.updateRazaoSocial(it)
                },
                widthPercentage = 1f
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextWithTextField(
                text = "Apelido/Nome Fantasia:",
                value = nomeFantasiaValue,
                charLimit = 60,
                onlyNumbers = false,
                onValueChange = {
                    viewModel.updateNomeFantasia(it)
                },
                widthPercentage = 1f
            )
        }
    }
}