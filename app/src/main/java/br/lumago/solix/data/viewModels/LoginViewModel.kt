package br.lumago.solix.data.viewModels

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.ui.home.HomeScreen

class LoginViewModel : ViewModel() {
    // TextFields
    var emailValue by mutableStateOf(TextFieldValue(""))
        private set

    // Open
    fun openHomeScreen(activity: Activity){
        val homeScreen = Intent(activity, HomeScreen::class.java)
        activity.startActivity(homeScreen)
        activity.finish()
    }

    class LoginViewModelFactory : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel() as T
        }
    }
}