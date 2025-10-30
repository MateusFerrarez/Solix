package br.lumago.solix.ui.signUp.components

import androidx.activity.compose.LocalActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.lumago.solix.data.viewModels.SignUpViewModel
import br.lumago.solix.ui.theme.boldStyle
import br.lumago.solix.ui.theme.corGradienteHome1
import br.lumago.solix.ui.utils.buttons.DefaultButton
import br.lumago.solix.ui.utils.components.DefaultTextField

@Composable
fun SignUpView(viewModel: SignUpViewModel) {
    // TextField
    val emailValue = viewModel.emailValue.collectAsState()
    val password1Value = viewModel.password1Value.collectAsState()
    val password2Value = viewModel.password2Value.collectAsState()
    // Show password
    var showPassword1 by remember { mutableStateOf(false) }
    var showPassword2 by remember { mutableStateOf(false)}
    //
    val activity = LocalActivity.current!!
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .background(corGradienteHome1),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .border(1.dp, Color.Transparent, RoundedCornerShape(16.dp))
                .padding(vertical = 24.dp, horizontal = 8.dp),
        ) {
            Text(
                text = "Se cadastre para desfrutar o nosso app!",
                style = boldStyle,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            DefaultTextField(
                label = "Email",
                field = emailValue.value,
                onTextFieldChange = { viewModel.updateEmail(it) },
                onIconClick = {},
                icon = Icons.Default.Person,
                isClickable = false,
                action = ImeAction.Next,
                isPassword = false,
                showPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            DefaultTextField(
                label = "Senha",
                field = password1Value.value,
                onTextFieldChange = { viewModel.updatePassword1(it) },
                onIconClick = {showPassword1 = !showPassword1},
                icon = Icons.Default.Lock,
                isClickable = true,
                action = ImeAction.Next,
                isPassword = true,
                showPassword = showPassword1
            )

            Spacer(modifier = Modifier.height(24.dp))

            DefaultTextField(
                label = "Confirmar senha",
                field = password2Value.value,
                onTextFieldChange = { viewModel.updatePassword2(it) },
                onIconClick = {showPassword2 = !showPassword2},
                icon = Icons.Default.Lock,
                isClickable = true,
                action = ImeAction.Next,
                isPassword = true,
                showPassword = showPassword2
            )

            Spacer(modifier = Modifier.height(24.dp))

            DefaultButton(
                onClick = {

                },
                text = "Cadastrar",
                fillMaxWidth = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            DefaultButton(
                onClick = {
                    backDispatcher?.onBackPressed()
                },
                text = "Voltar",
                fillMaxWidth = true
            )
        }
    }

}