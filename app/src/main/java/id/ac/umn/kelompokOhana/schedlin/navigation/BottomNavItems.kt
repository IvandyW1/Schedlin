package id.ac.umn.kelompokOhana.schedlin.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

//Merupakan sebuah model dari sebuah item navigasi
data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val route : String
)

//Merupakan list dari item yag ada di navigasi bawah
val items = listOf(
    BottomNavigationItem(
        title = "Calendar",
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange,
        hasNews = false,
        route = Pages.CalendarPage.name
    ),
    BottomNavigationItem(
        title = "Memo",
        selectedIcon = Icons.Filled.AccountBox,
        unselectedIcon = Icons.Outlined.AccountBox,
        hasNews = false,
        route = Pages.MemoPage.name
    ),
    BottomNavigationItem(
        title = "Create",
        selectedIcon = Icons.Filled.Add,
        unselectedIcon = Icons.Outlined.Add,
        hasNews = false,
        route = Pages.CreatePage.name
    ),
    BottomNavigationItem(
        title = "Activity",
        selectedIcon = Icons.Filled.Notifications,
        unselectedIcon = Icons.Outlined.Notifications,
        hasNews = false,
        badgeCount = 10,
        route = Pages.NotificationPage.name
    ),
    BottomNavigationItem(
        title = "Setting",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        hasNews = false,
        route = Pages.SettingPage.name
    ),
)