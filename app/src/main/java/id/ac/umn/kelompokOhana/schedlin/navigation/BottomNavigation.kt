package id.ac.umn.kelompokOhana.schedlin.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.ac.umn.kelompokOhana.schedlin.Pages.CalendarPage
import id.ac.umn.kelompokOhana.schedlin.Pages.CreatePage
import id.ac.umn.kelompokOhana.schedlin.Pages.MemoPage
import id.ac.umn.kelompokOhana.schedlin.Pages.NotificationsPage
import id.ac.umn.kelompokOhana.schedlin.Pages.SettingPage
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(){
    var selectedItemindex by rememberSaveable {
        mutableStateOf(0)
    }

    val navController = rememberNavController()

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "abcd")},
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu,
                            contentDescription = "Menu")
                    }
                }
            )
        },

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
                MemoPage()
            }
            composable(route = Pages.CreatePage.name){
                CreatePage()
            }
            composable(route = Pages.NotificationPage.name){
                NotificationsPage()
            }
            composable(route = Pages.SettingPage.name){
                SettingPage()
            }
        }

    }
}