package br.lumago.solix.ui.newPayment.components

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.handler.NewPaymentHandler
import br.lumago.solix.data.handler.PaymentHandler
import br.lumago.solix.data.viewModels.NewPaymentViewModel
import br.lumago.solix.ui.utils.buttons.DefaultButton
import br.lumago.solix.ui.utils.formatting.FormatDate
import br.lumago.solix.ui.utils.components.Header
import br.lumago.solix.ui.utils.components.ProgressCircle
import br.lumago.solix.ui.utils.components.TextWithButton
import br.lumago.solix.ui.utils.components.TextWithDatePicker
import br.lumago.solix.ui.utils.components.TextWithTextField
import br.lumago.solix.ui.utils.dialogs.StatusDialog

@Composable
fun NewPayment(viewModel: NewPaymentViewModel) {
    val activity = LocalActivity.current!!
    val title = viewModel.title
    // Objects
    val customerSelected by viewModel.customerSelected.collectAsState()
    val indicatorSelected by viewModel.indicatorSelected.collectAsState()
    val dueDateSelected by viewModel.dueDate.collectAsState()
    val contractDateSelected by viewModel.contractDate.collectAsState()
    // TextFields
    val paymentField = viewModel.paymentValue
    val observationField = viewModel.observationValue
    // Dialog
    val showDialog = viewModel.showDialog.collectAsState().value
    val exception = viewModel.exception.collectAsState().value
    // Extra
    val paymentIdExtra = activity.intent.getLongExtra("paymentId", 0L)
    // Progress
    val showProgress = viewModel.showProgress

    LaunchedEffect(Unit) {
        if (paymentIdExtra != 0L && customerSelected == null) {
            viewModel.getPaymentById(
                paymentIdExtra,
                activity
            )
        } else {
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
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Header(
            title,
            activity
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (!showProgress) {
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
                        onClick = {
                            try {
                                viewModel.savePayment(activity)
                            } catch (e: Exception) {
                                viewModel.updateDialog(true)
                            }
                        },
                        text = "Gravar"
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        } else {
            ProgressCircle()
        }
    }

    if (exception != null) {
        StatusDialog(
            onClick = { viewModel.updateDialog(false) },
            message = NewPaymentHandler(exception).formatException(),
            isError = true
        )
    }
}