package br.lumago.solix.ui.sync.components

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.viewModels.sync.SyncStatus
import br.lumago.solix.data.viewModels.sync.SyncViewModel
import br.lumago.solix.exceptions.handler.SyncExceptionHandler
import br.lumago.solix.ui.theme.corTexto
import br.lumago.solix.ui.theme.normalStyle
import br.lumago.solix.ui.utils.buttons.DefaultButton
import br.lumago.solix.ui.utils.components.Header

@Composable
fun SyncView(viewModel: SyncViewModel) {
    val activity = LocalActivity.current!!
    //
    val currentProgress by viewModel.currentSyncStatus.collectAsState()
    var progressText by remember { mutableStateOf("") }
    var textColor by remember { mutableStateOf(Color.Black) }
    val progressBarValue by viewModel.progressBarValue.collectAsState()
    //
    val exception = viewModel.exception.collectAsState().value

    if (exception != null) {
        SyncExceptionHandler(exception).saveLog(activity)
    }

    progressText = when (currentProgress) {
        is SyncStatus.Wating -> "Processo ainda não iniciado..."
        is SyncStatus.Loading -> ((currentProgress as SyncStatus.Loading).value)
        is SyncStatus.Finished -> "Sincronização finalizada com sucesso!"
        else -> ((currentProgress as SyncStatus.Error).message)
    }

    textColor = when (currentProgress) {
        is SyncStatus.Error -> Color.Red
        else -> Color.Black
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(
            title = "Sincronizar",
            activity = activity
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = progressText,
            style = normalStyle,
            color = textColor
        )

        Spacer(modifier = Modifier.height(15.dp))

        val animatedProgress by animateFloatAsState(
            targetValue = progressBarValue,
            animationSpec = tween(durationMillis = 350),
            label = "Barra de progresso da sincronização"
        )

        LinearProgressIndicator(
            progress = {
                animatedProgress
            },
            color = corTexto,
            trackColor = Color.Gray,
            modifier = Modifier.height(5.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        DefaultButton(
            onClick = {
                viewModel.startSync(activity)
            },
            text = "Sincronizar"
        )
    }
}