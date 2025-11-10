package br.lumago.solix.data.viewModels.customer

import android.app.Activity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.lumago.solix.data.api.CnpjApi
import br.lumago.solix.data.api.models.CustomerResponse
import br.lumago.solix.data.entities.Addresses
import br.lumago.solix.data.entities.Customers
import br.lumago.solix.data.repositories.CustomerRepository
import br.lumago.solix.exceptions.customerApi.InvalidCnpj
import br.lumago.solix.exceptions.customerApi.ManyRequestsException
import br.lumago.solix.exceptions.customerHandler.CustomerUpdateException
import br.lumago.solix.exceptions.customerHandler.EmptyCustomerNameException
import br.lumago.solix.exceptions.customerHandler.NewCustomerInsertException
import br.lumago.solix.ui.utils.formatting.FormatString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException

sealed class CnpjApiStatus {
    data class Loading(val value: String) : CnpjApiStatus()
    data class Error(val message: String) : CnpjApiStatus()
    object Finished : CnpjApiStatus()
}

class CustomerHandlerViewModel(private val repository: CustomerRepository) : ViewModel() {
    //
    var customer = MutableStateFlow<Customers?>(null)
        private set
    private val address = MutableStateFlow<Addresses?>(null)

    var customerReponse = MutableStateFlow<CustomerResponse?>(null)
        private set
    var currentCnpjApiStatus =
        MutableStateFlow<CnpjApiStatus>(CnpjApiStatus.Loading("Iniciando consulta..."))
        private set

    //
    var exception = MutableStateFlow<Exception?>(null)
        private set
    var statusButton = MutableStateFlow(true)
        private set

    // Dialogs
    var showCnpjApiDialog = MutableStateFlow(false)
        private set
    var showCepApiDialog = MutableStateFlow(false)
        private set

    // TextFields values
    var documento1Value = MutableStateFlow(TextFieldValue(""))
        private set
    var documento2Value = MutableStateFlow(TextFieldValue(""))
        private set
    var razaoSocialValue = MutableStateFlow(TextFieldValue(""))
        private set
    var nomeFantasiaValue = MutableStateFlow(TextFieldValue(""))
        private set
    var zipCodeValue = MutableStateFlow(TextFieldValue(""))
        private set
    var cityValue = MutableStateFlow(TextFieldValue(""))
        private set
    var stateValue = MutableStateFlow(TextFieldValue(""))
        private set
    var streetValue = MutableStateFlow(TextFieldValue(""))
        private set
    var neighborhoodValue = MutableStateFlow(TextFieldValue(""))
        private set
    var numberValue = MutableStateFlow(TextFieldValue(""))
        private set
    var complementValue = MutableStateFlow(TextFieldValue(""))
        private set
    var referenceValue = MutableStateFlow(TextFieldValue(""))
        private set
    var phone1Value = MutableStateFlow(TextFieldValue(""))
        private set
    var phone2Value = MutableStateFlow(TextFieldValue(""))
        private set
    var email1Value = MutableStateFlow(TextFieldValue(""))
        private set
    var email2Value = MutableStateFlow(TextFieldValue(""))
        private set

    // Insert
    private fun insertCustomer(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (razaoSocialValue.value.text.isEmpty()) {
                    throw EmptyCustomerNameException("A razão social do cliente não pode estar vazia!")
                }

                val nextId = "MOB${repository.getNextCustomerId()}"
                customer.value = Customers(
                    partnerId = nextId,
                    razaoSocial = razaoSocialValue.value.text,
                    nomeFantasia = nomeFantasiaValue.value.text,
                    documento1 = documento1Value.value.text,
                    documento2 = documento2Value.value.text,
                    status = "0"
                )

                address.value = Addresses(
                    street = streetValue.value.text,
                    neighborhood = neighborhoodValue.value.text,
                    number = numberValue.value.text,
                    reference = referenceValue.value.text,
                    complement = complementValue.value.text,
                    zipCode = zipCodeValue.value.text,
                    city = cityValue.value.text,
                    state = stateValue.value.text,
                    number1 = phone1Value.value.text,
                    number2 = phone2Value.value.text,
                    email1 = email1Value.value.text,
                    email2 = email2Value.value.text,
                    latitude = 0f,
                    longitude = 0f
                )

                repository.insertCustomerAndAddress(
                    customer.value!!,
                    address.value!!
                )

                activity.setResult(1)
                activity.finish()
            } catch (e: EmptyCustomerNameException) {
                exception.update { e }
                statusButton.update { true }
            } catch (e: Exception) {
                exception.update { NewCustomerInsertException(e.toString()) }
            }
        }
    }

    fun saveCustomer(activity: Activity) {
        statusButton.update { false }

        if (customer.value == null) {
            insertCustomer(activity)
        } else {
            updateCustomer(activity)
        }
    }

    // Api calls
    fun checkCnpjApi() {
        showCnpjApiDialog.update { true }
        currentCnpjApiStatus.update { CnpjApiStatus.Loading("Realizando consulta...") }

        viewModelScope.launch {
            try {
                val response = CnpjApi.retrofitService.getCustomerByCnpj(documento1Value.value.text)
                customerReponse.update { null }

                if (response.code() == 400 || response.code() == 404) {
                    throw InvalidCnpj("Erro, CNPJ inválido!")
                }

                if (response.code() == 429) {
                    throw ManyRequestsException("Erro. limite de requisições excedido.\nPor favor aguarde 1 minuto e tente novamente.")
                }

                customerReponse.update { response.body()!! }

                currentCnpjApiStatus.update { CnpjApiStatus.Finished }
            } catch (e: Exception) {
                when (e) {
                    is InvalidCnpj, is ManyRequestsException -> {
                        currentCnpjApiStatus.update { CnpjApiStatus.Error(e.message!!) }
                    }

                    is UnknownHostException -> {
                        currentCnpjApiStatus.update { CnpjApiStatus.Error("Erro ao conectar no serviço externo") }
                    }

                    else -> {
                        currentCnpjApiStatus.update { CnpjApiStatus.Error("Erro ao consultar CNPJ") }
                        exception.update { e }
                    }
                }
            }
        }
    }

    fun copyCnpjApiReponse() {
        documento2Value.update { TextFieldValue(text = customerReponse.value!!.estabelecimento.inscricoesEstadual[0].inscricao) }
        showCnpjApiDialog.update { false }
        razaoSocialValue.update { TextFieldValue(text = customerReponse.value!!.razaoSocial.uppercase()) }
        nomeFantasiaValue.update { TextFieldValue(text = customerReponse.value!!.estabelecimento.nomeFantasia.uppercase()) }
        zipCodeValue.update { TextFieldValue(text = customerReponse.value!!.estabelecimento.cep) }
        cityValue.update { TextFieldValue(text = FormatString.removeAcento(customerReponse.value!!.estabelecimento.cidade.nome)) }
        stateValue.update { TextFieldValue(text = customerReponse.value!!.estabelecimento.estado.sigla) }
        streetValue.update { TextFieldValue(text = FormatString.removeAcento(customerReponse.value!!.estabelecimento.logradouro)) }
        neighborhoodValue.update { TextFieldValue(text = FormatString.removeAcento(customerReponse.value!!.estabelecimento.bairro)) }
        numberValue.update { TextFieldValue(text = customerReponse.value!!.estabelecimento.numero) }
    }

    // Get
    suspend fun getCustomerById(customerId: Long) {
        if (customer.value == null) {
            val tempCustomer = repository.getCustomerById(customerId)
            val tempAddress = repository.getAddressByCustomerId(customerId)

            customer.update { tempCustomer }
            address.update { tempAddress }

            documento1Value.update { TextFieldValue(text = tempCustomer.documento1) }
            if (tempCustomer.documento2 != null) {
                documento2Value.update { TextFieldValue(text = tempCustomer.documento2!!) }
            }
            razaoSocialValue.update { TextFieldValue(text = tempCustomer.razaoSocial) }
            if (tempCustomer.nomeFantasia != null) {
                nomeFantasiaValue.update { TextFieldValue(text = tempCustomer.nomeFantasia!!) }
            }

            zipCodeValue.update { TextFieldValue(text = tempAddress.zipCode) }
            cityValue.update { TextFieldValue(text = tempAddress.city) }
            stateValue.update { TextFieldValue(text = tempAddress.state) }
            streetValue.update { TextFieldValue(text = tempAddress.street) }
            neighborhoodValue.update { TextFieldValue(text = tempAddress.neighborhood) }
            numberValue.update { TextFieldValue(text = tempAddress.number) }
            complementValue.update { TextFieldValue(text = tempAddress.complement) }
            referenceValue.update { TextFieldValue(text = tempAddress.reference) }
            phone1Value.update { TextFieldValue(text = tempAddress.number1) }
            phone2Value.update { TextFieldValue(text = tempAddress.number2) }
            email1Value.update { TextFieldValue(text = tempAddress.email1) }
            email2Value.update { TextFieldValue(text = tempAddress.email2) }
        }
    }

    // Update
    fun updateException(ex: Exception?) {
        exception.update { ex }
    }

    fun updateApiDialog(value: Boolean) {
        showCnpjApiDialog.update { value }
    }

    fun updateDocumento1(value: TextFieldValue) {
        documento1Value.update { value }
    }

    fun updateDocumento2(value: TextFieldValue) {
        documento2Value.update { value }
    }

    fun updateRazaoSocial(value: TextFieldValue) {
        razaoSocialValue.update { value }
    }

    fun updateNomeFantasia(value: TextFieldValue) {
        nomeFantasiaValue.update { value }
    }

    fun updateZipCode(value: TextFieldValue) {
        zipCodeValue.update { value }
    }

    fun updateCity(value: TextFieldValue) {
        cityValue.update { value }
    }

    fun updateState(value: TextFieldValue) {
        stateValue.update { value }
    }

    fun updateStreet(value: TextFieldValue) {
        streetValue.update { value }
    }

    fun updateNeighborhood(value: TextFieldValue) {
        neighborhoodValue.update { value }
    }

    fun updateNumber(value: TextFieldValue) {
        numberValue.update { value }
    }

    fun updateComplement(value: TextFieldValue) {
        complementValue.update { value }
    }

    fun updateReference(value: TextFieldValue) {
        referenceValue.update { value }
    }

    fun updatePhone1(value: TextFieldValue) {
        phone1Value.update { value }
    }

    fun updatePhone2(value: TextFieldValue) {
        phone2Value.update { value }
    }

    fun updateEmail1(value: TextFieldValue) {
        email1Value.update { value }
    }

    fun updateEmail2(value: TextFieldValue) {
        email2Value.update { value }
    }

    fun updateCustomer(activity: Activity){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (razaoSocialValue.value.text.isEmpty()) {
                    throw EmptyCustomerNameException("A razão social do cliente não pode estar vazia!")
                }

                customer.value!!.documento1 = documento1Value.value.text
                customer.value!!.documento2 = documento2Value.value.text
                customer.value!!.razaoSocial = razaoSocialValue.value.text
                customer.value!!.nomeFantasia = nomeFantasiaValue.value.text

                address.value!!.zipCode = zipCodeValue.value.text
                address.value!!.city = cityValue.value.text
                address.value!!.state = stateValue.value.text
                address.value!!.street = streetValue.value.text
                address.value!!.neighborhood = neighborhoodValue.value.text
                address.value!!.number = numberValue.value.text
                address.value!!.complement = complementValue.value.text
                address.value!!.reference = referenceValue.value.text
                address.value!!.number1 = phone1Value.value.text
                address.value!!.number2 = phone2Value.value.text
                address.value!!.email1 = email1Value.value.text
                address.value!!.email2 = email2Value.value.text

                repository.updateCustomerAndAddress(
                    customer.value!!,
                    address.value!!
                )

                activity.setResult(2)
                activity.finish()
            } catch (e: EmptyCustomerNameException) {
                exception.update { e }
                statusButton.update { true }
            } catch (e: Exception) {
                exception.update { CustomerUpdateException(e.toString()) }
            }
        }
    }

    class CustomerHandlerViewModelFactory(private val repository: CustomerRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CustomerHandlerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CustomerHandlerViewModel(repository) as T
            }

            throw IllegalArgumentException("Unkow model view ${modelClass.name}")
        }
    }
}