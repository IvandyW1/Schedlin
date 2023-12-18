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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import id.ac.umn.kelompokOhana.schedlin.data.RegisterUIEvent
import id.ac.umn.kelompokOhana.schedlin.data.RegisterViewModel
import id.ac.umn.kelompokOhana.schedlin.navigation.AppRouter
import id.ac.umn.kelompokOhana.schedlin.navigation.Page


@Composable
fun RegisterPage(registerViewModel: RegisterViewModel = viewModel()) {

    Box(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center

    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                HeadingTextComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(15.dp))

                Image(
                    painter = painterResource(id = R.drawable.logo_no_background),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(256.dp)
                        .align(Alignment.CenterHorizontally)
                )

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.first_name),
                    painterResource = painterResource(id = R.drawable.profile),
                    onTextChanged = {
                        registerViewModel.onEvent(RegisterUIEvent.FirstNameChanged(it))
                    },
                    errorStatus = registerViewModel.registrationUIState.value.firstNameError
                )

                Spacer(modifier = Modifier.height(5.dp))

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.last_name),
                    painterResource = painterResource(id = R.drawable.profile),
                    onTextChanged = {
                        registerViewModel.onEvent(RegisterUIEvent.LastNameChanged(it))
                    },
                    errorStatus = registerViewModel.registrationUIState.value.lastNameError
                )

                Spacer(modifier = Modifier.height(5.dp))

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.email),
                    onTextChanged = {
                        registerViewModel.onEvent(RegisterUIEvent.EmailChanged(it))
                    },
                    errorStatus = registerViewModel.registrationUIState.value.emailError
                )

                Spacer(modifier = Modifier.height(5.dp))

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.ic_lock),
                    onTextChanged = {
                        registerViewModel.onEvent(RegisterUIEvent.PasswordChanged(it))
                    },
                    errorStatus = registerViewModel.registrationUIState.value.passwordError
                )


                Spacer(modifier = Modifier.height(65.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.register),
                    onButtonClicked = {
                        registerViewModel.onEvent(RegisterUIEvent.RegisterButtonClicked)
                    },
                    isEnabled = registerViewModel.allValidationsPassed.value
                )

                Spacer(modifier = Modifier.height(10.dp))
                DividerTextComponent()
                Spacer(modifier = Modifier.height(10.dp))

                ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                    AppRouter.navigateTo(Page.LoginPage)
                })

            }

        }
    }
}



@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {
    RegisterPage()
}