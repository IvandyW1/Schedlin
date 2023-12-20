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
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel
import id.ac.umn.kelompokOhana.schedlin.navigation.Pages
import id.ac.umn.kelompokOhana.schedlin.ui.theme.Background
import id.ac.umn.kelompokOhana.schedlin.ui.theme.SchedlinTheme

data class Memo(val id: String, val title: String, val content: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoPage(navController: NavController) {
    var memos by remember { mutableStateOf(dummyMemos) }
    val sViewModel = remember { SettingViewModel() }
    sViewModel.getMemosInfo()

    Scaffold(
        content = {
            LazyColumn(modifier = Modifier.fillMaxSize().background(Background)) {
                // Menampilkan daftar memo menggunakan komponen MemoCard
                items(memos) { memo ->
                    MemoCard(
                        memo = memo,
                        onDeleteClick = {
                            memos = memos - memo
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
    memo: Memo,
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
                    text = memo.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = memo.content, fontSize = 14.sp)
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

val dummyMemos = listOf(
    Memo("1", "Memo 1", "Content 1"),
    Memo("2", "Memo 2", "Content 2"),
    Memo("3", "Memo 3", "Content 3"),
    Memo("4", "Memo 4", "Content 4"),
    Memo("5", "Memo 5", "Content 5"),
    Memo("6", "Memo 6", "Content 6"),
    // Tambahkan data dummy sesuai kebutuhan Anda
)
