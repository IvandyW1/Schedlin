package id.ac.umn.kelompokOhana.schedlin.data

data class UserModel(
    var id: String,
    var name : String,
    var email : String,
    var profilePict : String,
    var calendars : ArrayList<String>,
    var memos : ArrayList<String>
)

object UserDataHolder {
    var currentUser: UserModel? = null
}

