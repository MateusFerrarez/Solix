package br.lumago.solix.ui.newCounter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import br.lumago.solix.data.repositories.ProductsRepository
import br.lumago.solix.data.viewModels.newCounter.NewCounterViewModel
import br.lumago.solix.ui.newCounter.components.NewCounterView

class NewCounterScreen : ComponentActivity() {
    private var requestCamera: ActivityResultLauncher<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (!it) {
                Toast.makeText(this, "Sem permissão", Toast.LENGTH_SHORT).show()
            }
        }
        val viewModel = ViewModelProvider(
            this,
            NewCounterViewModel.NewCounterViewModelFactory(
                ProductsRepository(this)
            )
        )[NewCounterViewModel::class.java]

        setContent {
            NewCounterView(
                requestCamera,
                viewModel
            )
        }
    }
}