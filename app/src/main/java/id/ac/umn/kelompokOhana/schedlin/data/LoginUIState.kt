package id.ac.umn.kelompokOhana.schedlin.data

//Digunakan untuk menyimpan keadaan input yang dimasukan user
data class LoginUIState (
    var email  :String = "",
    var password  :String = "",

    //Digunakan untuk mengecek kondisi apakah sudah sesuai aturan atau belum
    var emailError :Boolean = false,
    var passwordError : Boolean = false,
)