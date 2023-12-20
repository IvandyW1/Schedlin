package id.ac.umn.kelompokOhana.schedlin.data


//Digunakan untuk membuat model dari sebuah Event
data class EventModel(
    var id: String,
    var title : String,
    var uid : String,
    var desc : String,
    var startTime : String,
    var endTime : String,
    var date : String,
    var dateCreated: String,
)

//Digunakan untuk menyimpan Event saat ini
object EventDataHolder {
    var currentEvent: EventModel? = null
}