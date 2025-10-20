package br.lumago.solix.ui.payments.components

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.handler.PaymentHandler
import br.lumago.solix.data.viewModels.PaymentsViewModel
import br.lumago.solix.ui.newPayment.NewPaymentScreen
import br.lumago.solix.ui.utils.buttons.ActionButton
import br.lumago.solix.ui.utils.components.Header
import br.lumago.solix.ui.utils.dialogs.ChooserDialog
import br.lumago.solix.ui.utils.dialogs.StatusDialog

@Composable
fun PaymentsView(viewModel: PaymentsViewModel) {
    val activity = LocalActivity.current!!
    val payments = viewModel.payments.collectAsState().value
    //
    val showDialog = viewModel.showDialog.collectAsState().value
    val showChooserDialog = viewModel.showChooserDialog.collectAsState().value
    val dialogMessage = viewModel.dialogMessage.collectAsState().value
    //
    val exception = viewModel.exception.collectAsState().value

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == 1) {
            viewModel.getPayments(activity)
            viewModel.updateDialogMessage("Mensalidade cadastrada com sucesso!")
            viewModel.updateDialog(true)
        } else if (result.resultCode == 2){
            viewModel.getPayments(activity)
            viewModel.updateDialogMessage("Mensalidade atualizada com sucesso!")
            viewModel.updateDialog(true)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getPayments(activity)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column {
            Header(
                title = "Mensalidades",
                activity
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(10.dp)
            ) {
                items(payments, key = { payment -> payment.paymentId }) { payment ->
                    Spacer(modifier = Modifier.height(10.dp))

                    CardPayment(
                        payment,
                        onDeleteClick = { viewModel.updatePaymentId(payment) },
                        onCardClick = {
                            viewModel.openEditPaymentScreen(
                                launcher,
                                activity,
                                payment.paymentId
                            )
                        }
                    )
                }
            }
        }

        ActionButton(
            onClick = {
                viewModel.openNewPaymentScreen(launcher, activity)
            },
            text = "Nova mensalidade",
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.BottomEnd)
        )
    }

    if (showDialog) {
        StatusDialog(
            onClick = { viewModel.updateDialog(false) },
            message = dialogMessage,
            isError = false
        )
    }

    if (exception != null) {
        StatusDialog(
            onClick = { viewModel.updateException(null) },
            message = PaymentHandler(exception).formatException(),
            isError = true
        )
    }

    if (showChooserDialog) {
        ChooserDialog(
            onDimissClick = { viewModel.updateChooserDialog(false) },
            onPositiveClick = { viewModel.deletePaymentById(activity) },
            message = "Deseja mesmo excluir a mensalidade?"
        )
    }
}