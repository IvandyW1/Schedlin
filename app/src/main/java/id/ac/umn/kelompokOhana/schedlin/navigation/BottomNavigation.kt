package id.ac.umn.kelompokOhana.schedlin.navigation

import android.os.Build
import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.ac.umn.kelompokOhana.schedlin.Pages.CalendarPage
import id.ac.umn.kelompokOhana.schedlin.Pages.CreateCalendar
import id.ac.umn.kelompokOhana.schedlin.Pages.CreateEvent
import id.ac.umn.kelompokOhana.schedlin.Pages.CreateMemo
import id.ac.umn.kelompokOhana.schedlin.Pages.CreatePage
import id.ac.umn.kelompokOhana.schedlin.Pages.MemoDetailPage
import id.ac.umn.kelompokOhana.schedlin.Pages.MemoPage
import id.ac.umn.kelompokOhana.schedlin.Pages.NotificationsPage
import id.ac.umn.kelompokOhana.schedlin.Pages.SettingPage
import id.ac.umn.kelompokOhana.schedlin.R
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(){
    //Menyimpan index item yang di select saat ini
    var selectedItemindex by rememberSaveable {
        mutableStateOf(0)
    }
    //menyimpan navcontroller
    val navController = rememberNavController()

    Scaffold (
        //Digunakan untuk menampilkan appbar
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("SchedLin")
                }
            )
        },

        //Digunakan untuk menampilkan bottom bar
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemindex == index,
                        onClick = {
                            selectedItemindex = index
                            navController.navigate(item.route){
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(text = item.title)
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if(item.badgeCount !=null){
                                        Badge {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    }else if(item.hasNews){
                                        Badge()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if(index == selectedItemindex){
                                        item.selectedIcon
                                    }else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        })
                }
            }
        }
    ){paddingValues ->
        //Digunakan untuk mendefinisikan rute-rute serta melakukan perpindahan page
        NavHost(
            navController = navController,
            startDestination = Pages.CalendarPage.name,
            modifier = Modifier
                .padding(paddingValues)
        ){
            composable(route = Pages.CalendarPage.name){
                CalendarPage()
            }
            composable(route = Pages.MemoPage.name){
                MemoPage(navController = navController)
            }
            composable(route = "MemoDetailPage/{memoId}") { backStackEntry ->
                val memoId = backStackEntry.arguments?.getString("memoId")
                val dispatcher = LocalOnBackPressedDispatcherOwner.current
                Log.d("MemoDetailPage", "memoId: $memoId, dispatcher: $dispatcher")
                if (dispatcher != null && memoId != null) {
                    MemoDetailPage(memoId, dispatcher)
                } else {
                    Log.e("MemoDetailPage", "Failed to navigate to MemoDetailPage. memoId or dispatcher is null.")
                }
            }
            composable(route = Pages.CreatePage.name){
                CreatePage(navController = navController)
            }
            composable(route = Pages.NotificationPage.name){
                NotificationsPage()
            }
            composable(route = Pages.SettingPage.name){
                SettingPage()
            }
            composable(route = Pages.CreateCalendar.name) {
                CreateCalendar(navController = navController)
            }
            composable(route = Pages.CreateEvent.name) {
                CreateEvent(navController = navController)
            }
            composable(route = Pages.CreateMemo.name) {
                CreateMemo(navController = navController)
            }
        }

    }
}