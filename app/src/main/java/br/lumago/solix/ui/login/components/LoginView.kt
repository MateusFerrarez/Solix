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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.lumago.solix.data.viewModels.LoginViewModel
import br.lumago.solix.ui.theme.bgHomeBrush
import br.lumago.solix.ui.theme.fonts
import br.lumago.solix.ui.theme.titleStyle
import br.lumago.solix.ui.utils.buttons.DefaultButton
import br.lumago.solix.ui.utils.components.DefaultTextField
import br.lumago.solix.ui.utils.dialogs.StatusDialog

@Composable
fun LoginView(viewModel: LoginViewModel) {
    val activity = LocalActivity.current!!
    // TextFields
    val emailField = viewModel.emailValue.collectAsState()
    val passwordField = viewModel.passwordValue.collectAsState()
    var showPassword by remember { mutableStateOf(false) }
    // Dialogs
    val showMessageDialog = viewModel.showMessageDialog.collectAsState().value

    BackHandler {
        viewModel.updateMessageDialog(true)
    }

    if (showMessageDialog) {
        StatusDialog(
            onClick = { viewModel.updateMessageDialog(false) },
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
                    .width(355.dp)
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, Color.Transparent, RoundedCornerShape(16.dp))
                        .padding(vertical = 24.dp, horizontal = 8.dp),
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

                    Spacer(modifier = Modifier.height(25.dp))

                    Text(
                        text = "Login",
                        style = titleStyle,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    DefaultTextField(
                        label = "Email",
                        field = emailField.value,
                        onTextFieldChange = { viewModel.updateEmailField(it) },
                        onIconClick = {  },
                        icon = Icons.Default.Person,
                        isClickable = false,
                        isPassword = false,
                        showPassword = true,
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    DefaultTextField(
                        label = "Senha",
                        field = passwordField.value,
                        onTextFieldChange = { viewModel.updatePasswordField(it) },
                        onIconClick = { showPassword = !showPassword },
                        icon = Icons.Default.Lock,
                        isClickable = true,
                        showPassword = showPassword,
                        isPassword = true,
                        action = ImeAction.Done
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    DefaultButton(
                        onClick = { viewModel.openHomeScreen(activity) },
                        text = "Entrar",
                        fillMaxWidth = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(
                        onClick = {
                            viewModel.openSignUpScreen(activity)
                        },
                        colors = ButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.Gray
                        )
                    ) {
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