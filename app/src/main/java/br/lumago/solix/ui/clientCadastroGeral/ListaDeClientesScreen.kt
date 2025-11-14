package br.lumago.solix.ui.clientCadastroGeral

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class Cliente(
    val id: Int,
    val nome: String,
    val endereco: String,
    val cidade: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaDeClientesScreen(navController: NavHostController) {

    // Simular Clientes
    val clientes = listOf(
        Cliente(1457, "Igor Pirola", "Rua Teste, 201 - Centro", "Vargem Grande do Sul"),
        Cliente(1458, "Giovanna Sagioratto", "Av. Paulista, 900 - Bela Vista", "São Paulo"),
        Cliente(1459, "Mateus Ferrarez", "Rua das Flores, 50 - Jardim Europa", "Campinas"),
        Cliente(1460, "Lucca Soares", "Rua A, 100 - Centro", "Ribeirão Preto"),
        Cliente(1461, "Fernanda Costa", "Rua Verde, 300 - Centro", "Piracicaba")
    )

    var busca by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clientes", color = Color.White) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF295C3D)
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate("geral")
                },
                icon = { Icon(Icons.Filled.Add, contentDescription = "Novo Cliente") },
                text = { Text("Novo Cliente") },
                containerColor = Color(0xFF295C3D),
                contentColor = Color.White
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            OutlinedTextField(
                value = busca,
                onValueChange = { busca = it },
                placeholder = { Text("Buscar cliente...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(clientes) { cliente ->
                    ClienteCard(cliente)
                }
            }
        }
    }
}

@Composable
fun ClienteCard(cliente: Cliente) {
    Surface(
        color = Color(0xFFF2F2F2),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "${cliente.id} - ${cliente.nome}",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFF295C3D),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Endereço: ${cliente.endereco}",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}
