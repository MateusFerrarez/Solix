package br.lumago.solix.ui.barcodeReader.components

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.yourproject.BarcodeReaderView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BarcodeScannerView() {
    val context = LocalContext.current

    // Estado para armazenar o último código de barras detectado
    var detectedBarcode by remember { mutableStateOf<String?>(null) }

    // 1. Gerenciamento de Permissões da Câmera
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    // Solicita a permissão quando o Composable é inicializado
    LaunchedEffect(Unit) {
        // Apenas lança a requisição se a permissão não foi concedida e não há um "rationale" para mostrar
        if (!cameraPermissionState.status.isGranted && !cameraPermissionState.status.shouldShowRationale) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status.isGranted) {
        // 2. Setup do BarcodeDetector e CameraSource
        val barcodeDetector = remember {
            BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.EAN_13) // Você pode definir vários formatos aqui
                .build()
        }

        // Configura o processador do BarcodeDetector e o libera quando o Composable é descartado
        DisposableEffect(barcodeDetector) {
            if (!barcodeDetector.isOperational) {
                Log.w("BarcodeScannerScreen", "Dependências do detector (ex: Google Play Services) ainda não estão disponíveis.")
                Toast.makeText(context, "Dependências do detector não disponíveis.", Toast.LENGTH_LONG).show()
            } else {
                barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
                    override fun release() {
                        Log.d("BarcodeScannerScreen", "Barcode detector liberado.")
                    }

                    override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                        val barcodes = detections?.detectedItems
                        if (barcodes != null && barcodes.size() != 0) {
                            // Processa o primeiro código de barras detectado.
                            val barcode = barcodes.valueAt(0)
                            // Atualiza o estado na thread principal para acionar a recomposição da UI
                            detectedBarcode = barcode.rawValue

                            // Aqui você poderia adicionar lógica para parar a câmera,
                            // navegar para outra tela, etc., após a detecção.
                        }
                    }
                })
            }

            // Garante que o BarcodeDetector seja liberado quando o Composable sair da composição
            onDispose {
                barcodeDetector.release()
                Log.d("BarcodeScannerScreen", "Barcode detector liberado em onDispose.")
            }
        }

        // Inicializa CameraSource usando remember
        val cameraSource = remember {
            CameraSource.Builder(context, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080) // Alta resolução para melhor detecção
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .build()
        }

        // Layout principal: Câmera na parte superior, texto detectado na parte inferior
        Column(Modifier.fillMaxSize()) {
            // Pré-visualização da câmera preenche a maior parte da tela
            Box(Modifier.weight(1f)) {
                BarcodeReaderView(cameraSource)
            }
            // Exibe o código de barras detectado ou uma mensagem
            detectedBarcode?.let { barcode ->
                Text(
                    text = "Código de Barras Detectado: $barcode",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
            } ?: Text(
                text = "Aponte a câmera para um código de barras...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        }

    } else {
        // Se a permissão não foi concedida, exibe uma mensagem e um botão para solicitá-la
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                "A permissão da câmera é essencial para a leitura de códigos de barras. Por favor, conceda a permissão nas configurações."
            } else {
                "Permissão de câmera necessária para usar o leitor de código de barras."
            }
            Text(textToShow, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Solicitar Permissão")
            }
        }
    }
}