package br.lumago.solix.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.lumago.solix.ui.home.components.Home
import br.lumago.solix.ui.login.components.Login
import br.lumago.solix.ui.theme.SolixTheme

class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SolixTheme {
                Login()
            }
        }
    }
}