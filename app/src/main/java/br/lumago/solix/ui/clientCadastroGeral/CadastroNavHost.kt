package br.lumago.solix.ui.clientCadastroGeral

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun CadastroNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "lista_clientes"
    ) {
        composable("lista_clientes") { ListaDeClientesScreen(navController) }
        composable("geral") { CadastroGeralScreen(navController) }
        composable("endereco") { CadastroEnderecoScreen(navController) }
        composable("contato") { CadastroContatoScreen(navController) }
    }
}
