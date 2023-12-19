package id.ac.umn.kelompokOhana.schedlin.data

import java.util.Date

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

object EventDataHolder {
    var currentEvent: EventModel? = null
}