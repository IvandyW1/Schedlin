package id.ac.umn.kelompokOhana.schedlin.Pages

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.ac.umn.kelompokOhana.schedlin.data.CalendarDataHolder
import id.ac.umn.kelompokOhana.schedlin.data.CreateMemoViewModel
import id.ac.umn.kelompokOhana.schedlin.data.SettingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMemo(navController: NavController){
    //Menyimpan nama dan deskripsi memo
    var memoName by remember { mutableStateOf("") }
    var memoDescription by remember { mutableStateOf("") }

    //Menyimpan view model untuk memanggil function
    val cmViewModel = remember { CreateMemoViewModel() }

    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "New Memo",
                 style = TextStyle(fontSize = 24.sp)
            )

            OutlinedTextField(
                value = memoName,
                onValueChange = { newMemoName -> memoName = newMemoName },
                label = { Text("Enter memo name") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { }),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Blue,
                    focusedBorderColor = Color.Blue,
                    cursorColor = Color.Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = memoDescription,
                onValueChange = { newMemoDescription -> memoDescription = newMemoDescription },
                label = { Text("Enter memo description") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { }),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Blue,
                    focusedBorderColor = Color.Blue,
                    cursorColor = Color.Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )

            Button(
                onClick = {
                    CalendarDataHolder.currCalendar?.let { cmViewModel.createNewMemo(memoName, memoDescription, it.id) }
                    Toast.makeText(context, "Memo Created!", Toast.LENGTH_SHORT).show()
                    navController.navigate("CalendarPage") },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                enabled = (memoName.isNotEmpty() && memoDescription.isNotEmpty())
            ) {
                Text(text = "Finish")

            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "startDestination") {
        composable("startDestination") {
            CreateMemo(navController = navController)
        }
        composable("CalendarPage") {}
    }
}