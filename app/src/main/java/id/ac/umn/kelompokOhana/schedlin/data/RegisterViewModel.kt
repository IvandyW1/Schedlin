package id.ac.umn.kelompokOhana.schedlin.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import id.ac.umn.kelompokOhana.schedlin.navigation.AppRouter
import id.ac.umn.kelompokOhana.schedlin.navigation.Page
import java.util.Date

class RegisterViewModel : ViewModel() {
    private val TAG = RegisterViewModel::class.simpleName
    var registrationUIState = mutableStateOf(RegistrationUIState())
    var allValidationsPassed = mutableStateOf(false)
    fun onEvent(event: RegisterUIEvent) {
        validateDataWithRules()
        when (event) {
            is RegisterUIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    firstName = event.firstName
                )
                printState()
            }

            is RegisterUIEvent.LastNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    lastName = event.lastName
                )
                printState()
            }

            is RegisterUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )

                printState()
            }


            is RegisterUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
                printState()
            }

            is RegisterUIEvent.RegisterButtonClicked->{
                signUp()
            }

            else -> {}
        }
    }

    private fun signUp() {
        Log.d(TAG, "Inside_signUp")
        printState()
        createUserInFirebase(
            email = registrationUIState.value.email,
            password = registrationUIState.value.password,
            fName = registrationUIState.value.firstName,
            lName = registrationUIState.value.lastName
        )
    }

    private fun validateDataWithRules() {
        val fNameResult = Validator.validateFirstName(
            fName = registrationUIState.value.firstName
        )

        val lNameResult = Validator.validateLastName(
            lName = registrationUIState.value.lastName
        )

        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )

        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "fNameResult= $fNameResult")
        Log.d(TAG, "lNameResult= $lNameResult")
        Log.d(TAG, "emailResult= $emailResult")
        Log.d(TAG, "passwordResult= $passwordResult")

        registrationUIState.value = registrationUIState.value.copy(
            firstNameError = fNameResult.status,
            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
        )

        allValidationsPassed.value = fNameResult.status && lNameResult.status &&
                emailResult.status && passwordResult.status

    }

    private fun printState() {
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }

    private fun createUserInFirebase(email: String, password: String, fName:String, lName:String) {
        val db = Firebase.firestore

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside_OnCompleteListener")
                Log.d(TAG, " isSuccessful = ${it.isSuccessful}")

                if (it.isSuccessful) {
                    //Ngambil current user
                    val user = Firebase.auth.currentUser

                    if(user != null){

                        //Ngambil Reference ke storage buat default avatar image
                        val storage = Firebase.storage
                        val storageRef = storage.reference
                        val defaultAvatarRef = storageRef.child("avatar/defaultAvatar.png")
                        val path = defaultAvatarRef.path

                        val userList = arrayListOf(user.uid)

                        //Buat calendar default baru
                        val newCalendar = hashMapOf(
                            "usersId" to userList,
                            "name" to "Personal Calendar",
                        )

                        //Masukin ke collection
                        val newCalendarDocRef = db.collection("calendars").document()
                        newCalendarDocRef.set(newCalendar)

                        newCalendarDocRef.collection("events")

                        //Ambil reference calendar default baru
                        val newCalendarDocId = newCalendarDocRef.id
                        Log.d(TAG, newCalendarDocId)

                        val calendarsArray = arrayListOf(newCalendarDocId)

                        //Buat user baru
                        val newUser = hashMapOf(
                            "name" to "$fName $lName",
                            "email" to email,
                            "calendars" to calendarsArray,
                            "memos" to null,
                            "profilepict" to path
                        )

                        //Masukin user ke collection
                        db.collection("users").document(user.uid)
                            .set(newUser)

                        //Testing buat
                        val a = CreateCalendarViewModel()
                        a.createNewCalendar("testi")
                        a.addNewEvent(newCalendarDocId, Date(), "eventstes", "15.00", "17.00", "testningmemos")
                        val b = CreateMemoViewModel()
                        b.createNewMemo("memot", "inin tetsng", newCalendarDocId)
                        b.addNewMessages("7QaFpwNkRP4FCTCq02OH", "inin isis message", Date())
                    }

                    SettingViewModel().getUserInfo()

                    AppRouter.navigateTo(Page.HomePage)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside_OnFailureListener")
                Log.d(TAG, "Exception= ${it.message}")
                Log.d(TAG, "Exception= ${it.localizedMessage}")
            }



    }
}