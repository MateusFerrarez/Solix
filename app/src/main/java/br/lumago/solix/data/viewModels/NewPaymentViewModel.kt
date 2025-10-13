package br.lumago.solix.data.viewModels

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.lumago.solix.data.entities.Payments
import br.lumago.solix.data.entities.relations.CustomerSelected
import br.lumago.solix.data.repositories.PaymentsRepository
import br.lumago.solix.ui.utils.formatting.FormatCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime

class NewPaymentViewModel(private val repository: PaymentsRepository) : ViewModel() {
    private var payment: Payments? = null
    var title by mutableStateOf("Nova mensalidade")
        private set

    // Buttons
    var customerSelected = MutableStateFlow<CustomerSelected?>(null)
        private set

    var indicatorSelected = MutableStateFlow<CustomerSelected?>(null)
        private set

    var dueDate = MutableStateFlow<LocalDate>(LocalDate.now())
        private set

    var contractDate = MutableStateFlow<LocalDate>(LocalDate.now())
        private set

    // TextFields
    var paymentValue by mutableStateOf(TextFieldValue("R$ 0,00"))
        private set

    var observationValue by mutableStateOf(TextFieldValue(""))
        private set

    // Dialog
    var showDialog = MutableStateFlow(false)
        private set

    // Loading
    var showProgress by mutableStateOf(true)
        private set

    suspend fun mock() {
        customerSelected.value = CustomerSelected(
            "11 - MATEUS TESTE DE LARGURA DO CARD PARA VERIFICAR O TAMANHO DO OVERFLOX",
            1L
        )
        indicatorSelected.value = CustomerSelected("12 - MATEUS TESTE 2", 2L)
        delay(600)
        showProgress = false
    }

    // Insert
    fun insertPayment(activity: Activity) {
        val formatedPaymentValue = paymentValue.text
            .replace("R$", "")
            .replace(".", "")
            .replace(",", ".")
            .trim()
            .toDouble()

        payment = Payments(
            paymentId = 0L,
            enterpriseId = 1L,
            customerId = customerSelected.value!!.customerId,
            indicatorId = indicatorSelected.value?.customerId,
            monthValue = formatedPaymentValue,
            dueDate = dueDate.value.toString(),
            contractDate = contractDate.value.toString(),
            observation = observationValue.text,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = null,
            synchronizedAt = null
        )

        viewModelScope.launch {
            repository.insertPayment(payment!!)
            activity.setResult(1)
            activity.finish()
        }
    }

    fun savePayment(activity: Activity) {
        if (payment == null) {
            insertPayment(activity)
        } else {
            updatePayment(activity)
        }
    }

    // Get
    suspend fun getPaymentById(paymentId: Long) {
        try {
            title = "Editar mensalidade"

            payment = withContext(Dispatchers.IO) {
                repository.getPaymentById(paymentId)
            }

            if (payment == null) {
                throw Exception("Mensalide não pode ser nula")
            }

            customerSelected.value = withContext(Dispatchers.IO) {
                repository.getCustomerSelectedByPaymentId(paymentId)
            }

            indicatorSelected.value = withContext(Dispatchers.IO) {
                repository.getIndicatorSelectedByPaymentId(paymentId)
            }

            paymentValue = TextFieldValue(FormatCurrency().formatToReal(payment!!.monthValue))
            dueDate.value = LocalDate.parse(payment!!.dueDate)
            contractDate.value = LocalDate.parse(payment!!.contractDate)
            payment!!.observation?.let { obs ->
                observationValue = TextFieldValue(obs)
            }

            delay(500)
            showProgress = false
        } catch (e: Exception) {
            Log.d("--- DEV", "getPaymentById: EXCEPTION - $e")
        }
    }

    // Show
    fun showDatePicker(
        context: Context,
        date: LocalDate,
        onDateChanged: (LocalDate) -> Unit
    ) {
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val tempDate = LocalDate.of(year, month + 1, dayOfMonth)
            onDateChanged(tempDate)
        }

        val dialog = DatePickerDialog(
            context,
            dateListener,
            date.year,
            date.monthValue - 1,
            date.dayOfMonth
        )

        dialog.show()
    }

    // Update
    fun updateDialog(value: Boolean) {
        showDialog.update { value }
    }

    fun updatePaymentValue(tempNewPaymentValue: TextFieldValue) {
        paymentValue = tempNewPaymentValue
    }

    fun updateObservation(tempNewObservation: TextFieldValue) {
        observationValue = tempNewObservation
    }

    fun updateDueDate(tempDate: LocalDate) {
        dueDate.value = tempDate
    }

    fun updateContractDate(tempDate: LocalDate) {
        contractDate.value = tempDate
    }

    fun updatePayment(activity: Activity) {
        val formatedPaymentValue = paymentValue.text
            .replace("R$", "")
            .replace(".", "")
            .replace(",", ".")
            .trim()
            .toDouble()

        payment!!.customerId = customerSelected.value!!.customerId
        payment!!.indicatorId = indicatorSelected.value?.customerId
        payment!!.monthValue = formatedPaymentValue
        payment!!.dueDate= dueDate.value.toString()
        payment!!.contractDate = contractDate.value.toString()
        payment!!.observation = observationValue.text.uppercase()
        payment!!.updatedAt = LocalDateTime.now().toString()

        viewModelScope.launch {
            repository.updatePaymentByPayment(payment!!)
            activity.setResult(2)
            activity.finish()
        }
    }

    class NewPaymentViewModelFactory(private val repository: PaymentsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewPaymentViewModel(repository) as T
        }
    }
}