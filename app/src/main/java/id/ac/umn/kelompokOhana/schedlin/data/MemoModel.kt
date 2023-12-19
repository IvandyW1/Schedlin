package id.ac.umn.kelompokOhana.schedlin.data

data class MemoModel(
    var id: String,
    var name: String,
    var desc: String,
    var date: String,
    var usersId: ArrayList<String>,
    var cid: String,
    var messages: MutableList<MessageModel>
)

object MemoDataHolder {
    var memoList: MutableList<MemoModel> = mutableListOf()
}