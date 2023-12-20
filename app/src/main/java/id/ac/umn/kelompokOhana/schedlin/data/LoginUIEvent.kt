package id.ac.umn.kelompokOhana.schedlin.data

//Digunakan untuk tracking bahwa ada perubahan di input email dan password saat login
sealed class LoginUIEvent {
    data class EmailChanged(val email: String) : LoginUIEvent()
    data class PasswordChanged(val password: String) : LoginUIEvent()

    object LoginButtonClicked : LoginUIEvent()
}