package br.lumago.solix.data.viewModels.home

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.ui.customers.CustomersScreen
import br.lumago.solix.ui.payments.PaymentsScreen

class HomeViewModel() : ViewModel() {

    // Open
    fun openPaymentsScreen(activity: Activity) {
        val activityPaymentsScreen = Intent(activity, PaymentsScreen::class.java)
        activity.startActivity(activityPaymentsScreen)
    }

    fun openCustomersScreen(activity: Activity){
        val activityCustomersScreen = Intent(activity, CustomersScreen::class.java)
        activity.startActivity(activityCustomersScreen)
    }

    class HomeViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel() as T
        }
    }
}