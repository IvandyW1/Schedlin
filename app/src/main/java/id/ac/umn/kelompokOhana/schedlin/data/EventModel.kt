package id.ac.umn.kelompokOhana.schedlin.data

data class EventModel(
    var id: String,
    var title : String,
    var uid : String,
    var desc : String,
    var startTime : String,
    var endTime : String,
    var date : String,

)

object EventDataHolder {
    var currentEvent: EventModel? = null
}