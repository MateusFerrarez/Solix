package br.lumago.solix.ui.newCounter.components

import android.R
import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.data.viewModels.NewCounterViewModel
import br.lumago.solix.ui.barcodeReader.BarcodeReaderScreen
import br.lumago.solix.ui.payments.PaymentsScreen
import br.lumago.solix.ui.theme.corBotao
import br.lumago.solix.ui.theme.titleStyle
import br.lumago.solix.ui.utils.ActionButton
import br.lumago.solix.ui.utils.Header
import br.lumago.solix.ui.utils.PageTitle

@Composable
fun NewCounter(viewModel: NewCounterViewModel, requestCamera: ActivityResultLauncher<String>?) {
    val activity = LocalActivity.current!!
    val lista = viewModel.listaItem.collectAsState().value

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
                    onClick = {},
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
                activity.startActivity(activityBarcodeReader)
            },
            text = "Novo Produto",
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.BottomEnd)
        )
    }

}