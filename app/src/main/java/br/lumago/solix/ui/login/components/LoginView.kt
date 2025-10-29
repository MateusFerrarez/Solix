package br.lumago.solix.ui.login.components

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.viewModels.LoginViewModel
import br.lumago.solix.ui.theme.bgHomeBrush
import br.lumago.solix.ui.theme.fonts
import br.lumago.solix.ui.utils.buttons.DefaultButton
import br.lumago.solix.ui.utils.dialogs.StatusDialog

@Composable
fun LoginView(viewModel: LoginViewModel) {
    val activity = LocalActivity.current!!
    // TextFields
    val emailField = viewModel.emailValue.collectAsState()
    val passwordField = viewModel.passwordValue.collectAsState()
    // Dialogs
    val showMessageDialog = viewModel.showMessageDialog.collectAsState().value

    BackHandler {
        viewModel.updateMessageDialog(true)
    }

    if (showMessageDialog) {
        StatusDialog(
            onClick = {viewModel.updateMessageDialog(false)},
            message = "Deseja mesmo fechar o app?",
            isError = false
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgHomeBrush)
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
                        .padding(vertical = 24.dp, horizontal = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User Icon",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(70.dp)
                            .background(Color.LightGray, shape = CircleShape)
                            .padding(10.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = emailField.value,
                        onValueChange = { email -> viewModel.updateEmailField(email) },
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
                        value = passwordField.value,
                        onValueChange = { password -> viewModel.updatePasswordField(password) },
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

                    DefaultButton(
                        onClick = { viewModel.openHomeScreen(activity) },
                        text = "Entrar",
                        fillMaxWidth = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(onClick = {
                    }) {
                        Text(text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontFamily = fonts, color = Color.Black)) {
                                append("Não tem uma conta?")
                            }

                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fonts,
                                    color = Color.Black
                                ),
                            ) {
                                append(" Cadastre-se")
                            }
                        }
                        )
                    }
                }
            }
        }
    }
}