package id.ac.umn.kelompokOhana.schedlin.Pages

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.ac.umn.kelompokOhana.schedlin.R
import id.ac.umn.kelompokOhana.schedlin.components.ButtonComponent
import id.ac.umn.kelompokOhana.schedlin.components.HeadingTextComponent
import id.ac.umn.kelompokOhana.schedlin.data.CalendarDataHolder
import id.ac.umn.kelompokOhana.schedlin.data.RegisterUIEvent
import id.ac.umn.kelompokOhana.schedlin.data.RegisterViewModel
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel
import id.ac.umn.kelompokOhana.schedlin.data.UserDataHolder
import id.ac.umn.kelompokOhana.schedlin.notif.NotificationService

@Composable
fun SettingPage(){
    val settingViewModel = SettingViewModel()
    settingViewModel.getUserInfo()
    val userInfo = UserDataHolder.currentUser

    Column(
    ){
        Image(
            painter = painterResource(id = R.drawable.man),
            contentDescription = "User Profile",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )
        if (userInfo != null) {
            HeadingTextComponent(value = userInfo.name)
        }

        Spacer(modifier = Modifier.height(16.dp))
        ButtonComponent(
            value = stringResource(id = R.string.first_name),
            onButtonClicked = {
                settingViewModel.getUserInfo()
            },
            isEnabled = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        ButtonComponent(
            value = stringResource(id = R.string.first_name),
            onButtonClicked = {
                settingViewModel.getCalenderInfo()
            },
            isEnabled = true
        )

        ButtonComponent(
            value = stringResource(id = R.string.logout),
            onButtonClicked = {
                settingViewModel.logout()
            },
            isEnabled = true
        )
    }
}