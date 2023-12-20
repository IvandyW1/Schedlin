package id.ac.umn.kelompokOhana.schedlin.data

//Digunakan untuk membuat model dari sebuah Calendar
data class CalendarModel(
    var id: String,
    var name: String,
    var usersId: ArrayList<String>,
    var events: MutableList<EventModel>
)

//Digunakan untuk menyimpan list Calendar dari user saat ini
object CalendarDataHolder {
    var calendarList: MutableList<CalendarModel> = mutableListOf()
    var currCalendar: CalendarModel? = null
}