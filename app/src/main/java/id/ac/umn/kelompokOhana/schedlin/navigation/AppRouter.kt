package id.ac.umn.kelompokOhana.schedlin.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

//Berisi daftar page
sealed class Page {

    object RegisterPage : Page()
    object LoginPage : Page()
    object HomePage : Page()
}


object AppRouter {

    //Untuk menyimpan page saat ini
    var currentScreen: MutableState<Page> = mutableStateOf(
        //Apabila user sudah pernah login langsung diarahkan ke homepage
        if (Firebase.auth.currentUser != null) {
            Page.HomePage
        } else {
            Page.RegisterPage
        }
    )

    //Fungsi untuk melakukan perpindahan value currentScreen
    fun navigateTo(destination : Page){
        currentScreen.value = destination
    }

}