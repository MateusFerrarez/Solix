package br.lumago.solix.ui.counter

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.lumago.solix.ui.counter.components.CounterView


class CounterScreen : ComponentActivity() {
    companion object {
        private const val CAMERA_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        val permission = Manifest.permission.CAMERA

        if (ContextCompat.checkSelfPermission(this, permission)
            == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
            return
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(permission),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != CAMERA_REQUEST_CODE) return

        val permission = Manifest.permission.CAMERA

        if (grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
            return
        }

        val shouldShowRationale =
            ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

        if (shouldShowRationale) {
            showMessageOKCancel(
                "É necessário permitir o uso da câmera para continuar."
            ) { _, _ ->
                checkCameraPermission()
            }
        } else {
            showMessageOKCancel(
                "A permissão foi desativada permanentemente. Vá até as configurações para habilitar."
            ) { _, _ ->
                openAppSettings()
            }
        }
    }
    private fun showMessageOKCancel(
        message: String,
        okListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }
    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }
    private fun openCamera() {
        setContent {
            CounterView()
        }
    }
}