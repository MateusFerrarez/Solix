package br.lumago.solix.ui.customers.components

import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.viewModels.customer.CustomerViewModel
import br.lumago.solix.ui.utils.buttons.ActionButton
import br.lumago.solix.ui.utils.components.Header

@Composable
fun CustomersView(viewModel: CustomerViewModel) {
    val activity = LocalActivity.current!!
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column {
            Box {
                Header(
                    title = "Clientes",
                    activity = activity
                )

            }
        }
        ActionButton(
            onClick = {
                viewModel.openCustomerHandlerScreen(
                    activity,
                    launcher
                )
            },
            text = "Novo cliente",
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.BottomEnd)
        )
    }
}