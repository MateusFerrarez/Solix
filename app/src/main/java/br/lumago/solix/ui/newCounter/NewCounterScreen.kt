package br.lumago.solix.ui.newCounter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.repositories.CounterRepository
import br.lumago.solix.data.viewModels.NewCounterViewModel
import br.lumago.solix.ui.barcodeReader.BarcodeReaderScreen
import br.lumago.solix.ui.newCounter.components.NewCounter

class NewCounterScreen : ComponentActivity() {
    private var requestCamera: ActivityResultLauncher<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(
            this,
            NewCounterViewModel.NewCounterViewModelFactory(CounterRepository(this))
        )[NewCounterViewModel::class]
        requestCamera = registerForActivityResult(ActivityResultContracts.RequestPermission(),){
            if(!it){
                Toast.makeText(this, "Sem permissão", Toast.LENGTH_SHORT).show()
            }
        }
        setContent {
            NewCounter(viewModel, requestCamera)
        }

    }
}