package id.ac.umn.kelompokOhana.schedlin.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import id.ac.umn.kelompokOhana.schedlin.Pages.LoginPage
import id.ac.umn.kelompokOhana.schedlin.Pages.RegisterPage


@Composable
fun CollabApp() {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        //Menangani navigasi saat login, register dan home page
        Crossfade(targetState = AppRouter.currentScreen, label = "") { currentState ->
            when(currentState.value) {
                is Page.RegisterPage -> {
                    RegisterPage()
                }

                is Page.LoginPage -> {
                    LoginPage()
                }

                is Page.HomePage ->{
                    BottomNavigation()
                }

                else -> {""}
            }
        }

    }
}