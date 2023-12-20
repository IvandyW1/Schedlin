package id.ac.umn.kelompokOhana.schedlin.Pages

import android.util.Log
import android.widget.CalendarView
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import id.ac.umn.kelompokOhana.schedlin.data.CalendarDataHolder
import id.ac.umn.kelompokOhana.schedlin.data.CalendarModel
import id.ac.umn.kelompokOhana.schedlin.data.EventModel
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel
import id.ac.umn.kelompokOhana.schedlin.data.UserDataHolder
import id.ac.umn.kelompokOhana.schedlin.data.UserModel
import id.ac.umn.kelompokOhana.schedlin.notif.NotificationManager
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPage(){
    val today = Calendar.getInstance()
    var date by remember{
        mutableStateOf(formatDate(today.time))
    }
    var date2 by remember{
        mutableStateOf(formatDate2(today.time))
    }
    var events by remember { mutableStateOf(mutableListOf<EventModel>()) }
    var calendars by remember { mutableStateOf(mutableListOf<String>()) }

    val viewModel = remember { SettingViewModel() }

    val userInfo = UserDataHolder.currentUser
    Log.d("testing", userInfo.toString())
    viewModel.getCalenderInfo()

    var calendarInfo by remember { mutableStateOf(CalendarDataHolder.calendarList) }
    Log.d("testing", calendarInfo.toString())

    if(calendarInfo.isNotEmpty()){
        for (calendar in calendarInfo){
            if(!calendars.contains(calendar.name)){
                calendars.add(calendar.name)
            }
        }
    }
    var selectedText : String = CalendarDataHolder.currCalendar?.name ?: "Calendar"

    fun changeCalendar(name: String){
        for (calendar in calendarInfo){
            if(calendar.name == name){
                CalendarDataHolder.currCalendar = calendar
                events = calendar.events
            }
        }
        selectedText = name
        Log.d("tststststs", selectedText)
        Log.d("tststststs", CalendarDataHolder.currCalendar.toString())
    }


    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .height(400.dp)
            .background(Color.White)

    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
            ) {
                TextField(
                    value = selectedText,
                    onValueChange = { changeCalendar(selectedText) },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    calendars.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                changeCalendar(selectedText)
                            }
                        )
                    }
                }
            }
        }
        AndroidView(factory = { CalendarView(it) }, update = {
            it.setOnDateChangeListener { calendarView, year, month, day ->
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                }.time
                date = formatDate(selectedDate)
                date2 = formatDate2(selectedDate)
            }
        })

        Text(text = date)

        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(
                horizontal = 15.dp,
                vertical = 10.dp
            )
        )

        Text(text = "Calendar ID :")
        CalendarDataHolder.currCalendar?.let { Text(text = it.id) }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Events for $date:",
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {
            items(events) { event ->
                if(event.date == date2){
                    EventItem(event = event.title)
                }

            }
        }
    }
}


fun formatDate(inputDate: Date): String {
    val dateFormat = SimpleDateFormat("EEE, dd MMMM yyyy", Locale.getDefault())
    return dateFormat.format(inputDate)
}

fun formatDate2(inputDate: Date): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(inputDate)
}

@Composable
fun EventItem(event: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(8.dp))
        Text(text = event)
    }
}

@Preview
@Composable
fun CalendarPreview() {
    CalendarPage()
}