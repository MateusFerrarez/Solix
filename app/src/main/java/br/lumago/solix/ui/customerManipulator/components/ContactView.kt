package br.lumago.solix.ui.customerManipulator.components

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.viewModels.customer.CustomerHandlerViewModel
import br.lumago.solix.ui.utils.buttons.DefaultButton
import br.lumago.solix.ui.utils.components.TextWithTextField

@Composable
fun ContactView(viewModel: CustomerHandlerViewModel) {
    //
    val activity = LocalActivity.current!!
    // Textfields values
    val phone1Value = viewModel.phone1Value.collectAsState().value
    val phone2Value = viewModel.phone2Value.collectAsState().value
    val email1Value = viewModel.email1Value.collectAsState().value
    val email2Value = viewModel.email2Value.collectAsState().value
    //
    val buttonStatus = viewModel.buttonStatus.collectAsState().value

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
            TextWithTextField(
                text = "Telefone principal:",
                value = phone1Value,
                charLimit = 20,
                onlyNumbers = true,
                onValueChange = { viewModel.updatePhone1(it) },
                widthPercentage = 1f
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextWithTextField(
                text = "Telefone secundário:",
                value = phone2Value,
                charLimit = 20,
                onlyNumbers = true,
                onValueChange = { viewModel.updatePhone2(it) },
                widthPercentage = 1f
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextWithTextField(
                text = "Email principal:",
                value = email1Value,
                charLimit = 120,
                onlyNumbers = false,
                onValueChange = { viewModel.updateEmail1(it) },
                widthPercentage = 1f
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextWithTextField(
                text = "Email secundário:",
                value = email2Value,
                charLimit = 120,
                onlyNumbers = false,
                onValueChange = { viewModel.updateEmail2(it) },
                widthPercentage = 1f
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                DefaultButton(
                    onClick = {
                        viewModel.saveCustomer(activity = activity)
                    },
                    text = "Gravar",
                    isEnable = buttonStatus
                )
            }
        }
    }
}