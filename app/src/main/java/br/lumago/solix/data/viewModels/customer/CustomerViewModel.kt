package br.lumago.solix.data.viewModels.customer

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.ui.customerHandler.CustomerHandlerScreen

class CustomerViewModel() : ViewModel() {

    fun openCustomerHandlerScreen(
        activity: Activity,
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    ) {
        val activityCustomerHandlerScreen = Intent(activity, CustomerHandlerScreen::class.java)
        launcher.launch(activityCustomerHandlerScreen)
    }

    class CustomerViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CustomerViewModel() as T
        }
    }
}