package id.ac.umn.kelompokOhana.schedlin.data

//Digunakan untuk membuat model dari sebuah Message
data class MessageModel(
    var id: String,
    var content : String,
    var uid : String,
    var date : String
)

//Digunakan untuk menyimpan Message saat ini
object MessageDataHolder {
    var currentMessage: MessageModel? = null
}
