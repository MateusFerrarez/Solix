package br.lumago.solix.ui.clientCadastroGeral

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.lumago.solix.ui.clientCadastroGeral.components.CampoTexto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroContatoScreen(navController: NavHostController) {

    var telefone1 by remember { mutableStateOf("") }
    var telefone2 by remember { mutableStateOf("") }
    var email1 by remember { mutableStateOf("") }
    var email2 by remember { mutableStateOf("") }

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

            TabRow(selectedTabIndex = 2, containerColor = Color.Transparent) {
                Tab(selected = false, onClick = { navController.navigate("geral") }, text = { Text("Geral") })
                Tab(selected = false, onClick = { navController.navigate("endereco") }, text = { Text("Endereços") })
                Tab(selected = true, onClick = {}, text = { Text("Contato") })
            }

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto("Telefone Principal", telefone1) { telefone1 = it }
            CampoTexto("Telefone Secundário", telefone2) { telefone2 = it }
            CampoTexto("E-mail Principal", email1) { email1 = it }
            CampoTexto("E-mail Secundário", email2) { email2 = it }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* ação de gravação */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295C3D)),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(48.dp)
            ) {
                Text(
                    "Gravar",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
