package id.ac.umn.kelompokOhana.schedlin.data

import java.util.Date

data class MessageModel(
    var id: String,
    var content : String,
    var uid : String,
    var date : String
)

object MessageDataHolder {
    var currentMessage: MessageModel? = null
}
