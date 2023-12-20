package id.ac.umn.kelompokOhana.schedlin.data

//Digunakan untuk membuat model dari sebuah user
data class UserModel(
    var id: String,
    var name : String,
    var email : String,
    var profilePict : String,
    var calendars : ArrayList<String>,
    var memos : ArrayList<String>
)

//Digunakan untuk menyimpan user yang saat ini sedang login
object UserDataHolder {
    var currentUser: UserModel? = null
}

