package br.lumago.solix.data.viewModels

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.ui.home.HomeScreen
import br.lumago.solix.ui.signUp.SignUpScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.sign

class LoginViewModel : ViewModel() {
    // TextFields
    var emailValue: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue(""))
        private set
    var passwordValue: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue(""))
        private set

    // Open
    fun openHomeScreen(activity: Activity) {
        val homeScreen = Intent(activity, HomeScreen::class.java)
        activity.startActivity(homeScreen)
        activity.finish()
    }

    fun openSignUpScreen(activity: Activity){
        val signUpScreen = Intent(activity, SignUpScreen::class.java)
        activity.startActivity(signUpScreen)
    }

    // Dialog
    var showMessageDialog = MutableStateFlow(false)
        private set

    // Update
    fun updateEmailField(newValue: TextFieldValue) {
        emailValue.update { newValue }
    }

    fun updatePasswordField(newValue: TextFieldValue) {
        passwordValue.update { newValue }
    }

    fun updateMessageDialog(newValue: Boolean) {
        showMessageDialog.update { newValue }
    }

    class LoginViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel() as T
        }
    }
}