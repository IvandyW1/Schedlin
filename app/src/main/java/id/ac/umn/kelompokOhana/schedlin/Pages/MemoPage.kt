package id.ac.umn.kelompokOhana.schedlin.Pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.ac.umn.kelompokOhana.schedlin.data.CalendarDataHolder
import id.ac.umn.kelompokOhana.schedlin.data.CreateMemoViewModel
import id.ac.umn.kelompokOhana.schedlin.data.MemoDataHolder
import id.ac.umn.kelompokOhana.schedlin.data.MemoModel
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel
import id.ac.umn.kelompokOhana.schedlin.navigation.Pages
import id.ac.umn.kelompokOhana.schedlin.ui.theme.Background
import id.ac.umn.kelompokOhana.schedlin.ui.theme.SchedlinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoPage(navController: NavController) {
    val sViewModel = remember { SettingViewModel() }
    sViewModel.getMemosInfo()
    var memos by remember { mutableStateOf(MemoDataHolder.memoList) }
    val cmViewModel = remember { CreateMemoViewModel() }

    Scaffold(
        content = {paddingValues ->
            LazyColumn(modifier = Modifier.fillMaxSize().background(Background).padding(paddingValues)) {
                // Menampilkan daftar memo menggunakan komponen MemoCard
                items(memos) { memo ->
                    MemoCard(
                        memo = memo,
                        onDeleteClick = {
                            memos.remove(memo)
                            //cmViewModel.deleteMemo(memo.id)
                            sViewModel.getMemosInfo()
                        },
                        onMemoClick = {
                            // Navigasi ke MemoDetailPage saat memo di klik
                            navController.navigate("MemoDetailPage/${memo.id}")
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun MemoCard(
    memo: MemoModel,
    onDeleteClick: () -> Unit,
    onMemoClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onMemoClick(memo.id) } // Handle click event
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Teks memo
            Column {
                Text(
                    text = memo.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = memo.desc, fontSize = 14.sp)
            }

            // Ikon delete
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewMemoPage() {
    SchedlinTheme {
        val navController = rememberNavController()
        MemoPage(navController)
    }
}

@Composable
fun MemoApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Pages.MemoPage.name) {
        composable(route = Pages.MemoPage.name) {
            MemoPage(navController)
        }
        composable(route = "MemoDetailPage/{memoId}") { backStackEntry ->
            val memoId = backStackEntry.arguments?.getString("memoId")
            val dispatcher = LocalOnBackPressedDispatcherOwner.current
            if (dispatcher != null) {
                MemoDetailPage(memoId ?: "", dispatcher)
            }
        }
    }
}
