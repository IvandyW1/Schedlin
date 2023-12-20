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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.umn.kelompokOhana.schedlin.data.CreateMemoViewModel
import id.ac.umn.kelompokOhana.schedlin.data.MemoDataHolder
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel
import id.ac.umn.kelompokOhana.schedlin.ui.theme.SchedlinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoDetailPage(
    memoId: String,
    onBackPressedDispatcher: OnBackPressedDispatcherOwner,
) {
    // Load memo details based on memoId
    var memo by remember { mutableStateOf(MemoDataHolder.memoList.find { it.id == memoId } ) }
    var isEditing by remember { mutableStateOf(false) }
    var editedTitle by remember { mutableStateOf(memo?.let { TextFieldValue(it.name) }) }
    var editedContent by remember { mutableStateOf(memo?.let { TextFieldValue(it.desc) }) }
    val cmViewModel = remember { CreateMemoViewModel() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isEditing) {
                        editedTitle?.let {
                            BasicTextField(
                                value = it,
                                onValueChange = {
                                    editedTitle = it
                                    // Jika ingin melakukan perubahan judul secara langsung,
                                    // kita bisa memperbarui objek memo.
                                    // Jika tidak, perubahan judul hanya akan terlihat
                                    // ketika menyimpan (onSaveClick).
                                    memo = memo?.copy(name = it.text)
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    } else {
                        memo?.let {
                            Text(
                                text = it.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                    }
                },
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
                                // Update memo in the database
                                memo?.let { editedTitle?.let { it1 -> editedContent?.let {
                                        it2 -> cmViewModel.editMemo(it1.text, it2.text, it.id) } } }
                                MemoDataHolder.memoList = mutableListOf()
                            }
                        )
                    } else {
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
        content = {paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                (if (isEditing) editedTitle else memo?.let { TextFieldValue(it.name) })?.let {
                    BasicTextField(
                        value = it,
                        onValueChange = {
                            if (isEditing) {
                                editedTitle = it
                                memo = memo?.copy(name = it.text)
                            }
                        },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                (if (isEditing) editedContent else memo?.let { TextFieldValue(it.desc) })?.let {
                    BasicTextField(
                        value = it,
                        onValueChange = {
                            if (isEditing) {
                                editedContent = it
                                memo = memo?.copy(desc = it.text)
                            }
                        },
                        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
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


