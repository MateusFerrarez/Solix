package br.lumago.solix.ui.utils.dialogs

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.lumago.solix.data.viewModels.customer.CnpjApiStatus
import br.lumago.solix.data.viewModels.customer.CustomerHandlerViewModel
import br.lumago.solix.ui.theme.boldStyle
import br.lumago.solix.ui.theme.corCard
import br.lumago.solix.ui.theme.normalStyle
import br.lumago.solix.ui.utils.buttons.DefaultButton

@Composable
fun CnpjAPiDialog(
    viewModel: CustomerHandlerViewModel,
    onDismissClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    // Progress
    val currentProgress by viewModel.currentCnpjApiStatus.collectAsState()
    var progressText by remember { mutableStateOf("") }
    var textColor by remember { mutableStateOf(Color.Black) }
    //
    val customerResponse = viewModel.customerReponse.collectAsState().value

    progressText = when (currentProgress) {
        is CnpjApiStatus.Loading -> ((currentProgress as CnpjApiStatus.Loading).value)
        is CnpjApiStatus.Error -> ((currentProgress as CnpjApiStatus.Error).message)
        else -> "Consulta realizada com sucesso"
    }

    textColor = when (currentProgress) {
        is CnpjApiStatus.Error -> Color.Red
        else -> Color.Black
    }

    Dialog(
        onDismissRequest = {}
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = corCard()
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = progressText,
                        style = normalStyle,
                        color = textColor
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                if (customerResponse != null) {
                    Log.d("--- DEV", "CnpjAPiDialog: $customerResponse")
                    val endereco =
                        "${customerResponse.estabelecimento.tipoLogradouro} ${customerResponse.estabelecimento.logradouro}, ${customerResponse.estabelecimento.numero}"

                    Row {
                        Text(
                            text = "Razão social:",
                            style = boldStyle,
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = customerResponse.razaoSocial,
                            style = normalStyle,
                            fontSize = 13.sp,
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Row {
                        Text(
                            text = "Nome fantasia:",
                            style = boldStyle,
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = customerResponse.estabelecimento.nomeFantasia?: "",
                            style = normalStyle,
                            fontSize = 13.sp,
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Row {
                        Text(
                            text = "Inscrição estadual:",
                            style = boldStyle,
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = customerResponse.estabelecimento.inscricoesEstadual[0].inscricao,
                            style = normalStyle,
                            fontSize = 13.sp,
                        )
                    }

                    Row {
                        Text(
                            text = "Endereço:",
                            style = boldStyle,
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = endereco,
                            style = normalStyle,
                            fontSize = 13.sp,
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Row {
                        Text(
                            text = "Cidade:",
                            style = boldStyle,
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = "${customerResponse.estabelecimento.cidade.nome}/${customerResponse.estabelecimento.estado.sigla}",
                            style = normalStyle,
                            fontSize = 13.sp,
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DefaultButton(
                        onClick = { onDismissClick() },
                        text = "Cancelar",
                        isNegative = true
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    DefaultButton(
                        onClick = {
                            onPositiveClick()
                        },
                        text = "Confirmar",
                        isEnable = customerResponse != null
                    )
                }
            }
        }
    }
}