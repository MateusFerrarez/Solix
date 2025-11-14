package br.lumago.solix.ui.newCounter.components

import android.Manifest
import android.content.Intent
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.data.viewModels.newCounter.NewCounterViewModel
import br.lumago.solix.exceptions.handler.NewCounterExceptionHandler
import br.lumago.solix.ui.barcodeReader.BarcodeReaderScreen
import br.lumago.solix.ui.theme.corBotao
import br.lumago.solix.ui.theme.corTexto
import br.lumago.solix.ui.theme.titleStyle
import br.lumago.solix.ui.utils.buttons.ActionButton
import br.lumago.solix.ui.utils.buttons.DefaultButton
import br.lumago.solix.ui.utils.components.Header
import br.lumago.solix.ui.utils.dialogs.StatusDialog

@Composable
fun NewCounterView(requestCamera: ActivityResultLauncher<String>?, viewModel: NewCounterViewModel) {
    val activity = LocalActivity.current!!
    //
    val itemList = viewModel.itemCardList.collectAsState().value
    //
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            1 -> {
                val barcode = result.data?.getStringExtra("product") ?: ""
                viewModel.getItemCardByBarcode(barcode)
            }
        }
    }
    //
    val exception = viewModel.exception.collectAsState().value

    // UI
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Produtos contabilizados",
                    style = titleStyle,
                    color = corTexto
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.absolutePadding(20.dp)
            ) {
                Text(
                    text = "Quantidade total: ${itemList.size}",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(10.dp)
            ) {
                items(count = itemList.size) { index ->
                    ItemCard(itemList[index])
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    if (itemList.isNotEmpty()){
                        DefaultButton(
                            onClick = {
                                viewModel.saveCount(activity)
                            },
                            text = "Gravar contagem"
                        )
                    }
                }
            }
        }

        ActionButton(
            onClick = {
                requestCamera?.launch(Manifest.permission.CAMERA)
                val activityBarcodeReader = Intent(activity, BarcodeReaderScreen::class.java)
                launcher.launch(activityBarcodeReader)
            },
            text = "Novo Produto",
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.BottomEnd)
        )
    }

    if (exception != null) {
        NewCounterExceptionHandler(exception).saveLog(activity)
        StatusDialog(
            onClick = {
                viewModel.updateException(null)
            },
            message = NewCounterExceptionHandler(exception).formatException(),
            isError = true
        )
    }
}