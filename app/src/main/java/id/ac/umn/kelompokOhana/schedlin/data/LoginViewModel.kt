package id.ac.umn.kelompokOhana.schedlin.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import id.ac.umn.kelompokOhana.schedlin.navigation.AppRouter
import id.ac.umn.kelompokOhana.schedlin.navigation.Page


class LoginViewModel : ViewModel() {
    private val TAG = LoginViewModel::class.simpleName

    //Digunakan untuk menyimpan input dari user saat login
    var loginUIState = mutableStateOf(LoginUIState())

    //Digunakan untuk mengecek apakah semua input yang dimasukkan sudah memenuhi ketentuan
    var allValidationsPassed = mutableStateOf(false)

    //Digunakan untuk tracking apakah sedang melakukan proses login atau tidak
    var loginInProgress = mutableStateOf(false)

    //Fungsi untuk tracking dan menyimpan input yang dimasukkan user
    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }
        }
        //Digunakan untuk melakukan validasi berdasarkan ketentuan
        validateLoginUIDataWithRules()
    }

    //Digunakan untuk melakukan validasi input dan memberikan jawaban apakah sudah sesuai atau belum
    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
        //Hanya akan true apabila semua input memenuhi ketentuan
        //Ketentuan diambil dari validator
        allValidationsPassed.value = emailResult.status && passwordResult.status

    }

    //Fungsi untuk melakukan proses login user
    private fun login() {
        //Untuk menyimpan value yang dimasukkan
        val email = loginUIState.value.email
        val password = loginUIState.value.password


        //Melakukan proses autentikasi menggunakan firebase
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG,"Inside_login_success")
                Log.d(TAG,"${it.isSuccessful}")

                if(it.isSuccessful){
                    //Apabila berhasil maka akan diarahkan ke homepage
                    AppRouter.navigateTo(Page.HomePage)
                    val user = Firebase.auth.currentUser
                }
            }
            .addOnFailureListener {
                Log.d(TAG,"Inside_login_failure")
                Log.d(TAG,"${it.localizedMessage}")

                loginInProgress.value = false

            }
        //Digunakan untuk mendapatkan info user yang sedang login saat ini
        SettingViewModel().getUserInfo()

    }
}
