package id.ac.umn.kelompokOhana.schedlin.data

data class CalendarModel(
    var id: String,
    var name: String,
    var usersId: ArrayList<String>,
    var events: MutableList<EventModel>
)

object CalendarDataHolder {
    var calendarList: MutableList<CalendarModel> = mutableListOf()
}