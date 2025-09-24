package br.lumago.solix.ui.home.components

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.ui.payments.PaymentsScreen
import br.lumago.solix.ui.theme.bgHomeBrush
import br.lumago.solix.ui.theme.corBotao
import br.lumago.solix.ui.theme.corGradienteHome1
import br.lumago.solix.ui.theme.corGradienteHome2
import br.lumago.solix.ui.theme.titleStyle

@Composable
fun Home() {
    val activity = LocalActivity.current!!

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState(),
                true
            )
            .background(bgHomeBrush)
    ) {
        Spacer(modifier = Modifier.height(35.dp))

        Icon(
            painter = rememberVectorPainter(Icons.Default.Home),
            contentDescription = "Home icon",
            tint = corBotao,
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(55.dp))

        Text(
            text = "Bem-vindo!",
            style = titleStyle,
            fontSize = 40.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(25.dp))

        ButtonHome(
            painter = rememberVectorPainter(Icons.Default.Person),
            onClick = {},
            text = "Clientes"
        )

        ButtonHome(
            painter = rememberVectorPainter(Icons.Default.LocationOn),
            onClick = {},
            text = "Mapa"
        )

        ButtonHome(
            painter = rememberVectorPainter(Icons.Default.Person),
            onClick = {
                val activityPaymentsScreen = Intent(activity, PaymentsScreen::class.java)
                activity.startActivity(activityPaymentsScreen)
            },
            text = "Mensalidades"
        )

        ButtonHome(
            painter = rememberVectorPainter(Icons.Default.Delete),
            onClick = {},
            text = "Contagens"
        )

        ButtonHome(
            painter = rememberVectorPainter(Icons.Default.Delete),
            onClick = {},
            text = "Sincronização"
        )

        Spacer(modifier = Modifier.height(15.dp))
    }
}