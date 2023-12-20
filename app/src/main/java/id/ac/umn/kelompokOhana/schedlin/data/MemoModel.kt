package id.ac.umn.kelompokOhana.schedlin.data

//Digunakan untuk membuat model dari sebuah Memo
data class MemoModel(
    var id: String,
    var name: String,
    var desc: String,
    var date: String,
    var usersId: ArrayList<String>,
    var cid: String,
    var messages: MutableList<MessageModel>
)

//Digunakan untuk menyimpan list Memo dari user saat ini
object MemoDataHolder {
    var memoList: MutableList<MemoModel> = mutableListOf()
}