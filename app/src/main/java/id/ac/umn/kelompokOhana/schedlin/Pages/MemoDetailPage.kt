package id.ac.umn.kelompokOhana.schedlin.Pages

//import androidx.compose.material3.icons.Icons
//import androidx.compose.material3.icons.filled.Edit
//import androidx.compose.material3.icons.filled.MoreVert
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.umn.kelompokOhana.schedlin.ui.theme.SchedlinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoDetailPage(
    memoId: String,
    onBackPressedDispatcher: OnBackPressedDispatcherOwner,
) {
    // Load memo details based on memoId
    val memo = dummyMemos.find { it.id == memoId } ?: Memo("", "", "")
    var isEditing by remember { mutableStateOf(false) }
    var editedTitle by remember { mutableStateOf(TextFieldValue(memo.title)) }
    var editedContent by remember { mutableStateOf(TextFieldValue(memo.content)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Memo Detail") },
                navigationIcon = {
                    IconButton(onClick = {
                        // Handle back button
                        if (isEditing) {
                            isEditing = false
                        } else {
                            onBackPressedDispatcher.onBackPressedDispatcher.onBackPressed()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Show edit and more actions in the top app bar
                    if (isEditing) {
                        EditActions(
                            onSaveClick = {
                                // Save edited memo
                                isEditing = false
                                // Update memo in the list or database
                            }
                        )
                    } else {
                        IconButton(onClick = {
                            // Handle more actions
                            // Show a menu with options like Edit and Delete
                            // You can use PopupProperties to create a menu
                        }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                        }
                        IconButton(onClick = {
                            // Start editing the memo
                            isEditing = true
                        }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Judul
                Text(
                    text = if (isEditing) editedTitle.text else memo.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                // Content
                BasicTextField(
                    value = if (isEditing) editedContent else TextFieldValue(memo.content),
                    onValueChange = {
                        if (isEditing) editedContent = it
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp)
                )
            }
        }
    )
}

@Composable
fun EditActions(onSaveClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(end = 16.dp)
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onSaveClick) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewMemoDetailPage() {
    SchedlinTheme {
        MemoDetailPage("1", LocalContext.current as OnBackPressedDispatcherOwner)
    }
}

