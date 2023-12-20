package id.ac.umn.kelompokOhana.schedlin.Pages

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.ac.umn.kelompokOhana.schedlin.R
import id.ac.umn.kelompokOhana.schedlin.components.ButtonComponent
import id.ac.umn.kelompokOhana.schedlin.components.HeadingTextComponent
import id.ac.umn.kelompokOhana.schedlin.data.CalendarDataHolder
import id.ac.umn.kelompokOhana.schedlin.data.RegisterUIEvent
import id.ac.umn.kelompokOhana.schedlin.data.RegisterViewModel
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel
import id.ac.umn.kelompokOhana.schedlin.data.UserDataHolder
import id.ac.umn.kelompokOhana.schedlin.notif.NotificationService
import id.ac.umn.kelompokOhana.schedlin.ui.theme.Background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingPage(){
    val settingViewModel = SettingViewModel()
    settingViewModel.getUserInfo()
    val userInfo = UserDataHolder.currentUser
    var newCalId by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().background(Background)
    ){
        Image(
            painter = painterResource(id = R.drawable.man),
            contentDescription = "User Profile",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 25.dp, bottom = 10.dp)
        )
        if (userInfo != null) {
            HeadingTextComponent(value = userInfo.name)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newCalId,
            onValueChange = { newId -> newCalId = newId },
            label = { Text("Enter Calender Id to Join") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { }),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = Color.Blue,
                focusedBorderColor = Color.Blue,
                cursorColor = Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(top = 15.dp, bottom = 15.dp)
        )

        ButtonComponent(
            value = stringResource(id = R.string.addCal),
            onButtonClicked = {
                settingViewModel.joinCalendar(newCalId)
                Toast.makeText(context, "Calendar Joined!", Toast.LENGTH_SHORT).show()
            },
            isEnabled = newCalId.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        ButtonComponent(
            value = stringResource(id = R.string.logout),
            onButtonClicked = {
                settingViewModel.logout()
            },
            isEnabled = true
        )
    }
}