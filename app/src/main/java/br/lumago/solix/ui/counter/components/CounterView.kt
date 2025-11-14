package br.lumago.solix.ui.counter.components

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.viewModels.count.CountViewModel
import br.lumago.solix.exceptions.handler.CountExceptionHandler
import br.lumago.solix.ui.newCounter.NewCounterScreen
import br.lumago.solix.ui.theme.corTexto
import br.lumago.solix.ui.theme.titleStyle
import br.lumago.solix.ui.utils.buttons.ActionButton
import br.lumago.solix.ui.utils.components.Header
import br.lumago.solix.ui.utils.dialogs.StatusDialog

@Composable
fun CounterView(viewModel: CountViewModel) {
    val activity = LocalActivity.current!!
    //
    val countList = viewModel.countList.collectAsState().value
    // Dialog
    val showDialog = viewModel.showDialog.collectAsState().value
    val dialogMessage = viewModel.dialogMessage.collectAsState().value
    //
    val exception = viewModel.exception.collectAsState().value
    //
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            1 -> {
                viewModel.updateDialogMessage("Contagem cadastrada com sucesso")
                viewModel.updateDialog(true)
                viewModel.getCounts()
            }
        }
    }

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
                "Contagens",
                activity
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Últimas contagens",
                    style = titleStyle,
                    color = corTexto
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(10.dp)
            ) {
                items(items = countList, key = { it.countId }) {
                    CountCard(it)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        ActionButton(
            onClick = {
                val newCounterScreen = Intent(activity, NewCounterScreen::class.java)
                launcher.launch(newCounterScreen)
            },
            text = "Nova Contagem",
            modifier = Modifier
                .padding(5.dp)
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

    if (exception != null){
        CountExceptionHandler(exception).saveLog(activity)
        StatusDialog(
            onClick = { viewModel.updateException(null) },
            message = CountExceptionHandler(exception).formatException(),
            isError = true
        )
    }
}