package id.ac.umn.kelompokOhana.schedlin.Pages

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import id.ac.umn.kelompokOhana.schedlin.data.CreateCalendarViewModel
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCalendar(){
    var calendarName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val createCalendarViewModel = remember { CreateCalendarViewModel() }

    Box(
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {createCalendarViewModel.createNewCalendar(calendarName)},
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Finish")
            }
        }
    }
}