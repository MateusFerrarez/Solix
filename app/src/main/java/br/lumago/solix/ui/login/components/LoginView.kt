package br.lumago.solix.ui.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val PrimaryColor = Color(0xFF2E473F) // Verde Escuro do Botão
private val BackgroundStartColor = Color(0xFFC8E6C9)
private val BackgroundEndColor = Color(0xFF7CB342)

@Preview(showBackground = true)
@Composable
fun Login() {
    val gradient = Brush.linearGradient(
        colors = listOf(BackgroundStartColor, BackgroundEndColor),
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
            ) {


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                        .padding(24.dp)
                        .padding(top = 45.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(30.dp))

                    // Campo de E-mail
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("E-mail") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFEEEEEE),
                            unfocusedContainerColor = Color(0xFFEEEEEE)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Senha") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFEEEEEE),
                            unfocusedContainerColor = Color(0xFFEEEEEE)
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {

                            println("Email: $email, Senha: $password")
                        },

                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Entrar", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(12.dp))


                    TextButton(onClick = {  }) {
                        Text("Não tem uma conta? Cadastre-se", color = Color.Gray)
                    }
                }

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User Icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(90.dp)
                        .background(Color.LightGray, shape = CircleShape)
                        .border(4.dp, Color.White, CircleShape) // borda
                        .clip(CircleShape)
                        .align(Alignment.TopCenter)
                        .offset(y = (-45).dp) 
                )
            }
        }
    }
}