package id.ac.umn.kelompokOhana.schedlin.Pages

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.CalendarView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CircleNotifications
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
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
import androidx.compose.runtime.DisposableEffect
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
import id.ac.umn.kelompokOhana.schedlin.data.CreateCalendarViewModel
import id.ac.umn.kelompokOhana.schedlin.data.EventModel
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel
import id.ac.umn.kelompokOhana.schedlin.data.UserDataHolder
import id.ac.umn.kelompokOhana.schedlin.ui.theme.Background
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPage(){
    //Mendapatkan tanggal hari ini
    val today = Calendar.getInstance()
    var date by remember{
        mutableStateOf(formatDate(today.time))
    }
    var date2 by remember{
        mutableStateOf(formatDate2(today.time))
    }

    //Menyimpan events dari tanggal yang dipilih
    var events by remember { mutableStateOf(mutableListOf<EventModel>()) }

    //Menyimpan nama-nama calendar yang dimiliki user untuk ditampilkan
    var calendars by remember { mutableStateOf(mutableListOf<String>()) }

    //Menyimpan view model setting agar dapat memanggil function
    val viewModel = remember { SettingViewModel() }
    viewModel.getUserInfo()

    //Menyimpan user info
    val userInfo = UserDataHolder.currentUser
    Log.d("testing", userInfo.toString())
    //Mendapatkan info kalender
    viewModel.getCalenderInfo()

    //Menyimpan calender list yang dimiliki user
    var calendarInfo by remember { mutableStateOf(CalendarDataHolder.calendarList) }
    Log.d("testing", calendarInfo.toString())

    //Mengambil nama-nama kalendar user
    if(calendarInfo.isNotEmpty()){
        for (calendar in calendarInfo){
            if(!calendars.contains(calendar.name)){
                calendars.add(calendar.name)
            }
        }
    }

    //Menyimpan kalender yang dipilih
    var selectedText : String = CalendarDataHolder.currCalendar?.name ?: "Calendar"

    //Mengganti kalendar yang dipilih
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
            .background(Background)
            .verticalScroll(rememberScrollState())
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

//        Text(text = "Calendar ID :")
//        CalendarDataHolder.currCalendar?.let { Text(text = it.id) }
        CopyButton(CalendarDataHolder.currCalendar?.id ?: "", LocalContext.current, selectedText != "Calendar")

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Events for $date:",
            modifier = Modifier.padding(16.dp)
        )

        for (event in events) {
            if (event.date == date2) {
                EventItem(event)
            }
        }
//        LazyColumn {
//            items(events) { event ->
//                if(event.date == date2){
//                    EventItem(event)
//                }
//
//            }
//        }
    }
}

//Mengubah format tanggal untuk ditampilkan
fun formatDate(inputDate: Date): String {
    val dateFormat = SimpleDateFormat("EEE, dd MMMM yyyy", Locale.getDefault())
    return dateFormat.format(inputDate)
}

//Mengubah format tanggal untuk disimpan
fun formatDate2(inputDate: Date): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(inputDate)
}

//Merupakan tampilan untuk tiap event
@Composable
fun EventItem(event : EventModel) {
    val ccviewModel = remember { CreateCalendarViewModel() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Default.CircleNotifications, contentDescription = "event list")
        }
        Column {
            Text(text = event.title)
            Text(text = event.startTime + " - " + event.endTime)
        }

        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {
            CalendarDataHolder.currCalendar?.let { ccviewModel.deleteEvent(event.id, it.id) }
        }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Button")
        }
    }
}

//Fungsi untuk tampilan Calendar Id dan Icon
@Composable
fun CopyButton(calendarId: String, context: Context, isCalendarSelected: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Calendar ID:")
        }

        Spacer(modifier = Modifier.height(10.dp))

//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = calendarId)
//            IconButton(
//                onClick = { copyToClipboard(calendarId, context) },
//                modifier = Modifier.fillMaxHeight().size(24.dp).align(Alignment.CenterVertically)
//            ) {
//                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(16.dp))
//            }
//        }
        // Tambahkan kondisi isCalendarSelected
        if (isCalendarSelected) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = calendarId)
                IconButton(
                    onClick = { copyToClipboard(calendarId, context) },
                    modifier = Modifier.fillMaxHeight().size(24.dp).align(Alignment.CenterVertically)
                ) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(16.dp))
                }
            }
        }
    }
    showToast("Copied to clipboard", context)
}

//Fungsi untuk copy calendar Id ke Clipboard user
fun copyToClipboard(text: String, context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Calendar ID", text)
    clipboard.setPrimaryClip(clip)
}

//Toast handle copy clipboard
@Composable
fun showToast(message: String, context: Context) {
    DisposableEffect(context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        onDispose { /* cleanup logic if needed */ }
    }
}



@Preview
@Composable
fun CalendarPreview() {
    CalendarPage()
}