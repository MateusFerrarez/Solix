package br.lumago.solix.ui.customerManipulator.components

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.viewModels.customer.CustomerHandlerViewModel
import br.lumago.solix.exceptions.handler.CustomerManipulatorExceptionHandler
import br.lumago.solix.ui.theme.boldStyle
import br.lumago.solix.ui.theme.corTexto
import br.lumago.solix.ui.utils.components.Header
import br.lumago.solix.ui.utils.dialogs.CnpjAPiDialog
import br.lumago.solix.ui.utils.dialogs.StatusDialog
import kotlinx.coroutines.launch

enum class CustomerTabs(
    val text: String,
) {
    Geral("Geral"),
    Endereco("Endereço"),
    Contato("Contato")
}

@Composable
fun CustomerHandlerView(viewModel: CustomerHandlerViewModel) {
    val activity = LocalActivity.current!!
    // Extra
    val customerIdExtra = activity.intent.getLongExtra("customerId", 0L)
    val isSynchronizedExtra = activity.intent.getBooleanExtra("isSynchronized", false)
    //
    var title = when (customerIdExtra) {
        0L -> "Novo Cliente"
        else -> "Editar cliente"
    }

    if (isSynchronizedExtra) {
        title = "Modo consulta"
    }
    // Dialogs
    val exception = viewModel.exception.collectAsState().value
    val showCnpjApiDialog = viewModel.showCnpjApiDialog.collectAsState().value
    // Pager
    val pagerState = rememberPagerState(pageCount = { CustomerTabs.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val scope = rememberCoroutineScope()

    if (showCnpjApiDialog) {
        CnpjAPiDialog(
            viewModel,
            onDismissClick = {
                viewModel.updateApiDialog(false)
            },
            onPositiveClick = {
                viewModel.copyCnpjApiReponse()
            }
        )
    }

    if (exception != null) {
        CustomerManipulatorExceptionHandler(exception).saveLog(activity)
        StatusDialog(
            onClick = {
                viewModel.updateException(null)
            },
            isError = true,
            message = CustomerManipulatorExceptionHandler(exception).formatException()
        )
    }

    LaunchedEffect(Unit) {
        if (customerIdExtra != 0L) {
            viewModel.getCustomerById(customerIdExtra)
        }
    }

    // UI
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Header(
            title,
            activity
        )

        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth(),
            contentColor = Color.Black,
            containerColor = Color.White,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .height(4.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(corTexto, shape = RoundedCornerShape(50))
                )
            }
        ) {
            CustomerTabs.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = {
                        Text(
                            text = currentTab.text,
                            style = boldStyle
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            when (page) {
                0 -> GeralView(viewModel)
                1 -> AddressView(viewModel)
                else -> ContactView(viewModel)
            }
        }
    }
}