package br.lumago.solix.data.viewModels

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : ViewModel() {
    var emailValue = MutableStateFlow(TextFieldValue(""))
        private set
    var password1Value = MutableStateFlow(TextFieldValue(""))
        private set
    var password2Value = MutableStateFlow(TextFieldValue(""))
        private set

    // Update
    fun updateEmail(newValue: TextFieldValue){
        emailValue.update { newValue }
    }

    fun updatePassword1(newValue: TextFieldValue){
        password1Value.update { newValue }
    }

    fun updatePassword2(newValue: TextFieldValue){
        password2Value.update { newValue }
    }

    class SignUpViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignUpViewModel() as T
        }
    }
}