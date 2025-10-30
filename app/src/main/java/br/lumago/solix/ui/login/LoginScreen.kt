package br.lumago.solix.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.viewModels.LoginViewModel
import br.lumago.solix.ui.login.components.LoginView

class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginViewModel = ViewModelProvider(
            this,
            LoginViewModel.LoginViewModelFactory()
        )[LoginViewModel::class]

        setContent {
            LoginView(loginViewModel)
        }
    }
}