package id.ac.umn.kelompokOhana.schedlin.Pages

import android.util.Log
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import id.ac.umn.kelompokOhana.schedlin.data.CalendarDataHolder
import id.ac.umn.kelompokOhana.schedlin.data.CalendarModel
import id.ac.umn.kelompokOhana.schedlin.data.EventModel
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel
import id.ac.umn.kelompokOhana.schedlin.data.UserDataHolder
import id.ac.umn.kelompokOhana.schedlin.data.UserModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CalendarPage(){
    val today = Calendar.getInstance()
    var date by remember{
        mutableStateOf(formatDate(today.time))
    }
    var events by remember { mutableStateOf(mutableListOf<EventModel>()) }

    val viewModel = remember { SettingViewModel() }

//    var userInfo = UserDataHolder.currentUser!!
//    Log.d("testing", userInfo.toString())
//    viewModel.getCalenderInfo()
//
//    var calendarInfo = CalendarDataHolder.calendarList
//    Log.d("testing", calendarInfo.toString())

//    var currentIndex = 0
//    events = calendarInfo[currentIndex].events


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .height(400.dp)
            .background(Color.White)

    ) {
        AndroidView(factory = { CalendarView(it) }, update = {
            it.setOnDateChangeListener { calendarView, year, month, day ->
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                }.time
                date = formatDate(selectedDate)
            }
        })
        Text(text = date)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Events for $date:",
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {
            items(events) { event ->
                EventItem(event = event.title)
            }
        }
    }
}

fun formatDate(inputDate: Date): String {
    val dateFormat = SimpleDateFormat("EEE, dd MMMM yyyy", Locale.getDefault())
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