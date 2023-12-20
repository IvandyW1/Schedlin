package id.ac.umn.kelompokOhana.schedlin.data

//Digunakan untuk menyimpan keadaan input yang dimasukan user
data class RegistrationUIState (
    var firstName :String = "",
    var lastName  :String = "",
    var email  :String = "",
    var password  :String = "",

    //Digunakan untuk mengecek kondisi apakah sudah sesuai aturan atau belum
    var firstNameError :Boolean = false,
    var lastNameError : Boolean = false,
    var emailError :Boolean = false,
    var passwordError : Boolean = false,
)