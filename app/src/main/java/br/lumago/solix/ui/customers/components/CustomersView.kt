package br.lumago.solix.ui.customers.components

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import br.lumago.solix.data.viewModels.customer.CustomerViewModel
import br.lumago.solix.exceptions.handler.CustomerExceptionHandler
import br.lumago.solix.ui.utils.buttons.ActionButton
import br.lumago.solix.ui.utils.components.CircleProgress
import br.lumago.solix.ui.utils.components.DefaultSearchBar
import br.lumago.solix.ui.utils.components.Header
import br.lumago.solix.ui.utils.dialogs.ChooserDialog
import br.lumago.solix.ui.utils.dialogs.StatusDialog

@Composable
fun CustomersView(viewModel: CustomerViewModel) {
    val activity = LocalActivity.current!!
    val pagingFlow = viewModel.pagingFlow.collectAsState().value
    val lazyItems = pagingFlow?.collectAsLazyPagingItems()
    //
    val busca by viewModel.searchValue.collectAsState()
    val buscaBanco by viewModel.queryValue.collectAsState()
    //
    val showDialog = viewModel.showDialog.collectAsState().value
    val showChooserDialog = viewModel.showChooserDialog.collectAsState().value
    val dialogMessage = viewModel.dialogMessage.collectAsState().value
    //
    val exception = viewModel.exception.collectAsState().value
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            1, 2 -> {
                val tempMsg = when (result.resultCode) {
                    1 -> "Cliente cadastrado com sucesso!"
                    2 -> "Cliente modificado com sucesso!"
                    else -> ""
                }
                viewModel.getCustomers(buscaBanco)
                viewModel.updateDialogMessage(tempMsg)
                viewModel.updateDialog(true)
            }
        }
    }

    // UI
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

                Column(horizontalAlignment = Alignment.Start) {
                    Spacer(modifier = Modifier.height(60.dp))
                    DefaultSearchBar(
                        busca = busca,
                        onChange = { viewModel.updateBusca(it) },
                        activity = activity
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        if (lazyItems != null) {
                            items(count = lazyItems.itemCount) { index ->
                                lazyItems[index]?.let {
                                    Spacer(modifier = Modifier.height(10.dp))

                                    CustomerCard(
                                        it,
                                        onCardClick = {
                                            viewModel.openEditCustomerScreen(
                                                activity,
                                                launcher,
                                                it.customerId,
                                                it.isSync,
                                            )
                                        },
                                        onIconClick = {
                                            viewModel.updateSelectedCustomer(it)
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
                viewModel.openNewCustomerScreen(
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

    if (exception != null) {
        CustomerExceptionHandler(exception).saveLog(activity)
        StatusDialog(
            onClick = { viewModel.updateException(null) },
            message = CustomerExceptionHandler(exception).formatException(),
            isError = true
        )
    }

    if (showDialog) {
        StatusDialog(
            onClick = { viewModel.updateDialog(false) },
            message = dialogMessage,
            isError = false
        )
    }

    if (showChooserDialog) {
        ChooserDialog(
            onDimissClick = { viewModel.updateChooserDialog(false) },
            onPositiveClick = { viewModel.deleteCustomerById() },
            message = "Deseja mesmo excluir cliente?"
        )
    }
}