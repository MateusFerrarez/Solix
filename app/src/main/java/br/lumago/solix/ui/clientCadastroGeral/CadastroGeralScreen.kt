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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroGeralScreen(navController: NavHostController) {

    var codigo by remember { mutableStateOf("") }
    var cnpjCpf by remember { mutableStateOf("") }
    var inscricaoEstadual by remember { mutableStateOf("") }
    var nomeRazao by remember { mutableStateOf("") }
    var apelido by remember { mutableStateOf("") }

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

            TabRow(selectedTabIndex = 0, containerColor = Color.Transparent) {
                Tab(selected = true, onClick = {}, text = { Text("Geral") })
                Tab(selected = false, onClick = { navController.navigate("endereco") }, text = { Text("Endereços") })
                Tab(selected = false, onClick = { navController.navigate("contato") }, text = { Text("Contato") })
            }

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto("Código", codigo) { codigo = it }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = cnpjCpf,
                    onValueChange = { cnpjCpf = it },
                    label = { Text("CNPJ/CPF") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    singleLine = true
                )

                Button(
                    onClick = { /* ação da consulta */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF295C3D)),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text("Consulta CNPJ", color = Color.White)
                }
            }

            CampoTexto("Inscrição Estadual/RG", inscricaoEstadual) { inscricaoEstadual = it }
            CampoTexto("Nome/Razão Social", nomeRazao) { nomeRazao = it }
            CampoTexto("Apelido/Nome Fantasia", apelido) { apelido = it }
        }
    }
}
