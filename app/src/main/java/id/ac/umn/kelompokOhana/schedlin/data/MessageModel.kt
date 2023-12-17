package id.ac.umn.kelompokOhana.schedlin.data

data class MessageModel(
    var id: String,
    var content : String,
    var uid : String,
    var date : String
)

object MessageDataHolder {
    var currentMessage: MessageModel? = null
}
