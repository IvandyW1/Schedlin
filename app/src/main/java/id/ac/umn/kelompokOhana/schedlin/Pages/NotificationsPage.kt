package id.ac.umn.kelompokOhana.schedlin.Pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import id.ac.umn.kelompokOhana.schedlin.data.ActivityDataHolder
import id.ac.umn.kelompokOhana.schedlin.data.ActivityViewModel
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel

@Composable
fun NotificationsPage(){
    val notifications = listOf("Notification 1", "Notification 2", "Notification 3")
    var textContent :String
    var dateContent :String
    val settingviewModel = remember { SettingViewModel() }
    val activityviewModel = remember { ActivityViewModel() }
    settingviewModel.getCalenderInfo()
    settingviewModel.getMemosInfo()
    activityviewModel.getActivityList()
    val activityInfo = ActivityDataHolder.activityList
    Log.d("activity", activityInfo.toString())

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(Color(0xFFE3F2FD))
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(activityInfo.size) { index ->
                Surface(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                        .clip(shape = RoundedCornerShape(20.dp)),
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = activityInfo[index].title,
                            modifier = Modifier.padding(bottom = 5.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = activityInfo[index].type + " was created.")
                            Text(text = activityInfo[index].date)
                        }
                    }
                }
            }
        }
    }
}