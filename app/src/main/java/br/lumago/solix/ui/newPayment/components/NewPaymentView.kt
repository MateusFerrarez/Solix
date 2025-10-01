package br.lumago.solix.ui.newPayment.components

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.viewModels.NewPaymentViewModel
import br.lumago.solix.ui.utils.DefaultButton
import br.lumago.solix.ui.utils.FormatDate
import br.lumago.solix.ui.utils.Header
import br.lumago.solix.ui.utils.TextWithButton
import br.lumago.solix.ui.utils.TextWithDatePicker
import br.lumago.solix.ui.utils.TextWithTextField

@Composable
fun NewPayment(viewModel: NewPaymentViewModel) {
    val activity = LocalActivity.current!!
    val customerSelected by viewModel.customerSelected.collectAsState()
    val indicatorSelected by viewModel.indicatorSelected.collectAsState()
    val dueDateSelected by viewModel.dueDate.collectAsState()
    val contractDateSelected by viewModel.contractDate.collectAsState()
    //
    val paymentField = viewModel.paymentValue
    val observationField = viewModel.observationValue

    LaunchedEffect(Unit) {
        if (customerSelected == null) {
            viewModel.mock()
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(
                rememberScrollState(),
                true
            )
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Header(
            "Nova mensalidade",
            activity
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        ) {
            TextWithButton(
                text = "Cliente:",
                buttonText = customerSelected?.customer,
                widthPercentage = 0.99f,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextWithButton(
                text = "Indicação:",
                buttonText = indicatorSelected?.customer,
                widthPercentage = 0.99f,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextWithTextField(
                text = "Valor da mensalidade:",
                value = paymentField,
                onlyNumbers = true,
                onValueChange = { viewModel.updatePaymentValue(it) },
                widthPercentage = 0.50f
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextWithDatePicker(
                text = "Data de vencimento:",
                onSelectDate = {
                    viewModel.showDatePicker(
                        activity,
                        dueDateSelected
                    ) { viewModel.updateDueDate(it) }
                },
                buttonText = FormatDate(dueDateSelected.toString()).format()
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextWithDatePicker(
                text = "Data de contrato:",
                onSelectDate = {
                    viewModel.showDatePicker(
                        activity,
                        contractDateSelected
                    ) { viewModel.updateContractDate(it) }
                },
                buttonText = FormatDate(contractDateSelected.toString()).format()
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextWithTextField(
                text = "Observação:",
                value = observationField,
                minHeight = 150.dp,
                onlyNumbers = false,
                onValueChange = { viewModel.updateObservation(it) },
                widthPercentage = 0.99f,
                roundedPercentage = 10
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                DefaultButton(
                    onClick = {},
                    text = "Gravar"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}