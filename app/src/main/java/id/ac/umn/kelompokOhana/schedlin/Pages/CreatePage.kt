package id.ac.umn.kelompokOhana.schedlin.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.ac.umn.kelompokOhana.schedlin.ui.theme.Background

@Composable
fun CreatePage(navController: NavHostController){
    val items = listOf("Calendar", "Event", "Memo")
    val listState = rememberLazyListState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(10.dp)
    ){
        LazyColumn(state = listState) {
            items(items) { item ->
                Text(text = item,
                     modifier = Modifier
                         .clickable {
                             when (item) {
                                 "Calendar" -> navController.navigate("CreateCalendar")
                                 "Event" -> navController.navigate("CreateEvent")
                                 "Memo" -> navController.navigate("CreateMemo")
                             }
                         }
                         .fillMaxWidth()
                         .padding(vertical = 15.dp))

                if(item != items.last()) {
                    Divider()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "startDestination") {
        composable("startDestination") {
            CreatePage(navController = navController)
        }
        composable("CreateCalendar") {
            Text(text = "Creating Calendar Page")
        }
        composable("CreateEvent") {
            Text(text = "Creating Event Page")
        }
        composable("CreateMemo") {
            Text(text = "Creating Memo Page")
        }
    }
}