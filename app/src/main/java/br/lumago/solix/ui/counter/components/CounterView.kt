package br.lumago.solix.ui.counter.components

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.lumago.solix.ui.utils.ActionButton
import br.lumago.solix.ui.utils.Header
import br.lumago.solix.ui.newCounter.NewCounterScreen
import br.lumago.solix.ui.utils.PageTitle

@Composable
fun CounterView() {
    val activity = LocalActivity.current!!

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

            PageTitle(
                title = "Últimas Contagens"
            )

            Spacer(modifier = Modifier.height(40.dp))


        }

        ActionButton(
            onClick = {
                val newCounterScreen = Intent(activity, NewCounterScreen::class.java)
                activity.startActivity(newCounterScreen)
            },
            text = "Nova Contagem",
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.BottomEnd)
        )
    }
}