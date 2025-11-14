package br.lumago.solix.ui.clientCadastroGeral

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.lumago.solix.ui.clientCadastroGeral.components.CampoTexto
import br.lumago.solix.ui.clientCadastroGeral.components.BotaoConsulta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroEnderecoScreen(navController: NavHostController) {

    var cep by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var uf by remember { mutableStateOf("") }
    var logradouro by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastro", color = Color.White) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF295C3D)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TabRow(selectedTabIndex = 1, containerColor = Color.Transparent) {
                Tab(selected = false, onClick = { navController.navigate("geral") }, text = { Text("Geral") })
                Tab(selected = true, onClick = {}, text = { Text("Endereços") })
                Tab(selected = false, onClick = { navController.navigate("contato") }, text = { Text("Contato") })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = cep,
                    onValueChange = { cep = it },
                    label = { Text("CEP") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    singleLine = true
                )

                BotaoConsulta(texto = "Consulta CEP") {
                    // TODO: ação de consulta CEP
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = cidade,
                    onValueChange = { cidade = it },
                    label = { Text("Cidade") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uf,
                    onValueChange = { uf = it },
                    label = { Text("UF") },
                    modifier = Modifier
                        .width(80.dp),
                    singleLine = true
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = logradouro,
                    onValueChange = { logradouro = it },
                    label = { Text("Logradouro") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = numero,
                    onValueChange = { numero = it },
                    label = { Text("Número") },
                    modifier = Modifier
                        .width(100.dp),
                    singleLine = true
                )
            }

            CampoTexto("Bairro", bairro) { bairro = it }
            CampoTexto("Complemento", complemento) { complemento = it }
            CampoTexto("Referência", referencia) { referencia = it }
        }
    }
}
