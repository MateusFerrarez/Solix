package br.lumago.solix.ui.payments.components

import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import br.lumago.solix.exceptions.handler.PaymentHandler
import br.lumago.solix.data.viewModels.payment.PaymentsViewModel
import br.lumago.solix.ui.theme.estiloFieldBusca
import br.lumago.solix.ui.utils.buttons.ActionButton
import br.lumago.solix.ui.utils.components.CircleProgress
import br.lumago.solix.ui.utils.components.Header
import br.lumago.solix.ui.utils.dialogs.ChooserDialog
import br.lumago.solix.ui.utils.dialogs.StatusDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsView(viewModel: PaymentsViewModel) {
    val activity = LocalActivity.current!!
    val pagingFlow = viewModel.pagingFlow.collectAsState().value
    val lazyItems = pagingFlow?.collectAsLazyPagingItems()
    //
    val showDialog = viewModel.showDialog.collectAsState().value
    val showChooserDialog = viewModel.showChooserDialog.collectAsState().value
    val dialogMessage = viewModel.dialogMessage.collectAsState().value
    //
    val exception = viewModel.exception.collectAsState().value
    val busca by viewModel.searchValue.collectAsState()
    val buscaBanco by viewModel.queryValue.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            1, 2 -> {
                val tempMsg = when (result.resultCode) {
                    1 -> "Mensalidade cadastrada com sucesso!"
                    2 -> "Mensalidade editada com sucesso!"
                    else -> ""
                }
                viewModel.getPayments(buscaBanco)
                viewModel.updateDialogMessage(tempMsg)
                viewModel.updateDialog(true)
            }
        }
    }

    val colors1 = SearchBarDefaults.colors(
        containerColor = Color.White
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column {
            Box {
                Header(
                    title = "Mensalidades",
                    activity
                )

                Column(horizontalAlignment = Alignment.Start) {
                    Spacer(modifier = Modifier.height(60.dp))

                    SearchBar(
                        inputField = {
                            TextField(
                                colors = estiloFieldBusca(),
                                modifier = Modifier.fillMaxWidth(),
                                shape = ShapeDefaults.Large,
                                value = busca,
                                onValueChange = { viewModel.updateBusca(it) },
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
                                                viewModel.updateBusca("")
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

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        if (lazyItems != null) {
                            items(lazyItems.itemCount) { index ->
                                lazyItems[index]?.let { payment ->
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

                            lazyItems.apply {
                                when {
                                    loadState.refresh is LoadState.Loading -> {
                                        item {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 15.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                            ) {
                                                CircleProgress()
                                            }
                                        }
                                    }

                                    loadState.append is LoadState.Loading -> {
                                        item {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 15.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                            ) {
                                                CircleProgress()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
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
        PaymentHandler(exception).saveLog(activity)
        StatusDialog(
            onClick = { viewModel.updateException(null) },
            message = PaymentHandler(exception).formatException(),
            isError = true
        )
    }

    if (showChooserDialog) {
        ChooserDialog(
            onDimissClick = { viewModel.updateChooserDialog(false) },
            onPositiveClick = { viewModel.deletePaymentById() },
            message = "Deseja mesmo excluir a mensalidade?"
        )
    }
}