package br.lumago.solix.data.viewModels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class CadastroGeralViewModel : ViewModel() {
    var codigo by mutableStateOf("")
    var cnpjCpf by mutableStateOf("")
    var inscricaoEstadual by mutableStateOf("")
    var nomeRazao by mutableStateOf("")
    var apelido by mutableStateOf("")

    fun consultarCnpj() {

    }
}
