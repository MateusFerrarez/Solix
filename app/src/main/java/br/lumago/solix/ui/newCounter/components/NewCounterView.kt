package br.lumago.solix.ui.newCounter.components

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import br.lumago.solix.data.entities.relations.ItemCard
import br.lumago.solix.data.viewModels.NewCounterViewModel
import br.lumago.solix.ui.barcodeReader.BarcodeReaderScreen
import br.lumago.solix.ui.theme.corBotao
import br.lumago.solix.ui.utils.ActionButton
import br.lumago.solix.ui.utils.Header
import br.lumago.solix.ui.utils.PageTitle


@Composable
fun NewCounter(viewModel: NewCounterViewModel, requestCamera: ActivityResultLauncher<String>?) {
    val activity = LocalActivity.current!!
    val lista = viewModel.listaItem.collectAsState().value
    val showErrorDialog = viewModel.showErrorDialog.collectAsState().value
    var showProductModal by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            1 -> {
                val retorno = result.data?.getStringExtra("product")
                val aux = lista.size
                viewModel.updateBarcode(retorno!!)
                if(aux != lista.size){

                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Header(
                "Contador",
                activity
            )

            Spacer(modifier = Modifier.height(20.dp))

            PageTitle(
                title = "Produtos Contabilizados"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        showProductModal = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = corBotao
                    ),
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = "Total por produto",
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.absolutePadding(20.dp)
            ) {
                Text(
                    text = "Quantidade total: ${lista.size}",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(lista, key = { index, item -> index }) { index, item ->
                    CardItem(item)
                    Spacer(modifier = Modifier.height((20.dp)))
                }
            }
        }

        ActionButton(
            onClick = {
                requestCamera?.launch(android.Manifest.permission.CAMERA)
                val activityBarcodeReader = Intent(activity, BarcodeReaderScreen::class.java)
                launcher.launch(activityBarcodeReader)
            },
            text = "Novo Produto",
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.BottomEnd)
        )

        ProductTableModal(
            showModal = showProductModal,
            onDismiss = { showProductModal = false },
            lista = lista
        )
        if (showErrorDialog) {
            ErrorAlertDialog(
                errorMessage = "Produto não encontrado, tente novamente",
                onDismiss = { viewModel.dismissError() }
            )
        }
    }
}

@Composable
fun ProductTableModal(
    showModal: Boolean,
    onDismiss: () -> Unit,
    lista: List<ItemCard>
) {
    val productCounts = remember(lista) {
        lista.groupingBy { it.descricao }
            .eachCount()
            .toList()
            .sortedBy { it.first }
    }

    if (showModal) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Total por Produtos",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${productCounts.size} produtos",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }

                        IconButton(onClick = onDismiss) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Fechar"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Cabeçalho da tabela
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.Gray.copy(alpha = 0.1f),
                                RoundedCornerShape(4.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Produto",
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Qtd.",
                            modifier = Modifier.width(80.dp),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        itemsIndexed(productCounts) { index, (productName, quantity) ->
                            ProductCountRow(
                                productName = productName,
                                quantity = quantity,
                                isEven = index % 2 == 0
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCountRow(
    productName: String,
    quantity: Int,
    isEven: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isEven) Color.Gray.copy(alpha = 0.05f) else Color.Transparent,
                RoundedCornerShape(4.dp)
            )
            .border(
                1.dp,
                Color.Gray.copy(alpha = 0.2f),
                RoundedCornerShape(4.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = productName,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )

        Box(
            modifier = Modifier
                .width(80.dp)
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    RoundedCornerShape(8.dp)
                )
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ErrorAlertDialog(
    errorMessage: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                Icons.Default.Warning,
                contentDescription = "Erro",
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(
                text = "Erro",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    )
}