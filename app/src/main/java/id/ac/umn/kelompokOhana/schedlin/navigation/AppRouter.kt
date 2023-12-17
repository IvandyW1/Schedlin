package id.ac.umn.kelompokOhana.schedlin.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

sealed class Page {

    object RegisterPage : Page()
    object LoginPage : Page()
    object HomePage : Page()
}


object AppRouter {

    var currentScreen: MutableState<Page> = mutableStateOf(
        if (Firebase.auth.currentUser != null) {
            Page.HomePage
        } else {
            Page.RegisterPage
        }
    )

    fun navigateTo(destination : Page){
        currentScreen.value = destination
    }

}