package id.ac.umn.kelompokOhana.schedlin.Pages

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.ac.umn.kelompokOhana.schedlin.data.CalendarDataHolder
import id.ac.umn.kelompokOhana.schedlin.data.CreateCalendarViewModel
import id.ac.umn.kelompokOhana.schedlin.data.CreateMemoViewModel
import id.ac.umn.kelompokOhana.schedlin.ui.theme.Background
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEvent(navController : NavController){
    var eventName by remember { mutableStateOf("") }
    var selectedDateText by remember { mutableStateOf("") }
    var selectedStartTimeText by remember { mutableStateOf("") }
    var selectedEndTimeText by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMonth
    )
    datePicker.datePicker.minDate = calendar.timeInMillis

    val startTimePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedStartTimeText = "$selectedHour:$selectedMinute"
        }, hour, minute, false
    )

    val endTimePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedEndTimeText = "$selectedHour:$selectedMinute"
        }, hour, minute, false
    )

    val ccViewModel = remember { CreateCalendarViewModel() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(Background)
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "New Event",
                 style = TextStyle(fontSize = 24.sp)
            )

            OutlinedTextField(
                value = eventName,
                onValueChange = { newEventName -> eventName = newEventName },
                label = { Text("Enter new event name") },
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
                    .padding(top = 15.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (selectedStartTimeText.isNotEmpty()) {
                    "Selected time is $selectedStartTimeText"
                } else {
                    "Please select the start time"
                }
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp),
                onClick = { startTimePicker.show() }
            ) {
                Text(text = "Start time")
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (selectedEndTimeText.isNotEmpty()) {
                    "Selected time is $selectedEndTimeText"
                } else {
                    "Please select the end time"
                }
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp),
                onClick = { endTimePicker.show() }
            ) {
                Text(text = "End time")
            }
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = eventDescription,
                onValueChange = { newEventDescription -> eventDescription = newEventDescription },
                label = { Text("Enter event description") },
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
                    .padding(top = 15.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (selectedDateText.isNotEmpty()) {
                    "Selected date is $selectedDateText"
                } else {
                    "Please pick a date"
                }
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp),
                onClick = { datePicker.show() }
            ) {
                Text(text = "Select a date")
            }

            Button(
                onClick = {
                    CalendarDataHolder.currCalendar?.let {
                        ccViewModel.addNewEvent(
                            it.id, selectedDateText,
                            eventName, selectedStartTimeText, selectedEndTimeText, eventDescription )
                    }
                    Toast.makeText(context, "Event Created!", Toast.LENGTH_SHORT).show()
                    navController.navigate("CalendarPage") },
                modifier = Modifier
                    .padding(vertical = 50.dp)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                enabled = (eventName.isNotEmpty() && eventDescription.isNotEmpty() &&
                        selectedDateText.isNotEmpty() && selectedEndTimeText.isNotEmpty()
                        && selectedStartTimeText.isNotEmpty())
            ) {
                Text(text = "Finish")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun eventPage() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "startDestination") {
        composable("startDestination") {
            CreateEvent(navController = navController)
        }
        composable("CalendarPage") {}
    }
}

