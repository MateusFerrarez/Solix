import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CadastroScreen() {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFB2F2BB), Color(0xFF69DB7C))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Cadastre-se",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "E-mail:",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2B5329),
                    fontSize = 14.sp
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(16.dp)), // bordas mais arredondadas
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Senha",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2B5329),
                    fontSize = 14.sp
                )
                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(16.dp)),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Confirmar senha",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2B5329),
                    fontSize = 14.sp
                )
                OutlinedTextField(
                    value = confirmarSenha,
                    onValueChange = { confirmarSenha = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(16.dp)),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )
            }

            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B5329)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Cadastrar", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
