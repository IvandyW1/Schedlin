package id.ac.umn.kelompokOhana.schedlin.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.ac.umn.kelompokOhana.schedlin.R
import id.ac.umn.kelompokOhana.schedlin.components.ButtonComponent
import id.ac.umn.kelompokOhana.schedlin.components.ClickableLoginTextComponent
import id.ac.umn.kelompokOhana.schedlin.components.DividerTextComponent
import id.ac.umn.kelompokOhana.schedlin.components.HeadingTextComponent
import id.ac.umn.kelompokOhana.schedlin.components.MyTextFieldComponent
import id.ac.umn.kelompokOhana.schedlin.components.PasswordTextFieldComponent
import id.ac.umn.kelompokOhana.schedlin.components.UnderLinedTextComponent
import id.ac.umn.kelompokOhana.schedlin.data.LoginUIEvent
import id.ac.umn.kelompokOhana.schedlin.data.LoginViewModel
import id.ac.umn.kelompokOhana.schedlin.data.RegisterUIEvent
import id.ac.umn.kelompokOhana.schedlin.data.RegisterViewModel
import id.ac.umn.kelompokOhana.schedlin.navigation.AppRouter
import id.ac.umn.kelompokOhana.schedlin.navigation.BackButtonHandler
import id.ac.umn.kelompokOhana.schedlin.navigation.Page


@Composable
fun LoginPage(loginViewModel: LoginViewModel = viewModel()){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.bg3),
                contentScale = ContentScale.FillBounds, alpha = 0.6F
            )
            .padding(28.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            HeadingTextComponent(value = stringResource(id = R.string.welcome))
            Spacer(modifier = Modifier.height(15.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_no_background),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(256.dp)
                    .align(Alignment.CenterHorizontally)
            )

            MyTextFieldComponent(labelValue = stringResource(id = R.string.email),
                painterResource(id = R.drawable.email),
                onTextChanged = {
                    loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                },
                errorStatus = loginViewModel.loginUIState.value.emailError
            )
            Spacer(modifier = Modifier.height(5.dp))
            PasswordTextFieldComponent(
                labelValue = stringResource(id = R.string.password),
                painterResource(id = R.drawable.ic_lock),
                onTextChanged = {
                    loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                },
                errorStatus = loginViewModel.loginUIState.value.passwordError
            )


            Spacer(modifier = Modifier.height(120.dp))
            ButtonComponent(
                value = stringResource(id = R.string.login),
                onButtonClicked = {
                    loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                },
                isEnabled = loginViewModel.allValidationsPassed.value
            )

            Spacer(modifier = Modifier.height(10.dp))
            DividerTextComponent()
            Spacer(modifier = Modifier.height(10.dp))

            ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                AppRouter.navigateTo(Page.RegisterPage)
            })
        }
    }

    BackButtonHandler {
        AppRouter.navigateTo(Page.RegisterPage)
    }
}

@Preview
@Composable
fun PreviewLogin() {
    LoginPage()
}