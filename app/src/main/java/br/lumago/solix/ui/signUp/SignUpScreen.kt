package br.lumago.solix.ui.signUp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.viewModels.SignUpViewModel
import br.lumago.solix.ui.signUp.components.SignUpView

class SignUpScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(
            this,
            SignUpViewModel.SignUpViewModelFactory()
        )[SignUpViewModel::class]

        setContent {
            SignUpView(viewModel)
        }
    }
}