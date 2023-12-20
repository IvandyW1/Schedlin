package id.ac.umn.kelompokOhana.schedlin.Pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import id.ac.umn.kelompokOhana.schedlin.R
import id.ac.umn.kelompokOhana.schedlin.components.MyTextFieldComponent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.ac.umn.kelompokOhana.schedlin.data.CreateCalendarViewModel
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel
import id.ac.umn.kelompokOhana.schedlin.ui.theme.Background


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCalendar(navController: NavController){

    //Menyimpan nama kalendar
    var calendarName by remember { mutableStateOf("") }
    val context = LocalContext.current

    //Menyimpan viewmodel untuk memanggil function
    val ccViewModel = remember { CreateCalendarViewModel() }
    val sViewModel = remember { SettingViewModel() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(Background)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "New Calendar",
                 style = TextStyle(fontSize = 24.sp)
            )

            OutlinedTextField(
                value = calendarName,
                onValueChange = { newCalendarName -> calendarName = newCalendarName },
                label = { Text("Enter new calendar name") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { }),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Blue,
                    focusedBorderColor = Color.Blue,
                    cursorColor = Color.Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    ccViewModel.createNewCalendar(calendarName)
                    sViewModel.getCalenderInfo()
                    Toast.makeText(context, "Calendar Created!", Toast.LENGTH_SHORT).show()
                    navController.navigate("CalendarPage")},
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                enabled = calendarName.isNotBlank()
            ) {
                Text(text = "Finish")
            }
        }
    }
}

@Composable
fun kalender() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "startDestination") {
        composable("startDestination") {
            CreateCalendar(navController = navController)
        }
        composable("CalendarPage") {}
    }
}