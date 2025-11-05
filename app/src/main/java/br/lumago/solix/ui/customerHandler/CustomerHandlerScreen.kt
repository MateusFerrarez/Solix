package br.lumago.solix.ui.customerHandler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.lumago.solix.ui.customerHandler.components.CustomerHandlerView

class CustomerHandlerScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomerHandlerView()
        }
    }
}