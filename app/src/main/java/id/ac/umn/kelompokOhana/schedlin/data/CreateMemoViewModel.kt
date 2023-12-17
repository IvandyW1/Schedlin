package id.ac.umn.kelompokOhana.schedlin.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateMemoViewModel : ViewModel() {
    val db = Firebase.firestore

    //Ngambil current user
    val user = Firebase.auth.currentUser


    //Ngambil Date
    fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(currentDate)
    }
    val date = getCurrentDate()

    //Membuat Memo Baru
    fun createNewMemo(name: String, desc: String, calId: String){

        val userList = arrayListOf(user?.uid)
        val newMemo = hashMapOf(
            "name" to name,
            "desc" to desc,
            "dateCreated" to date,
            "calendarID" to calId,
            "usersID" to userList
        )

        //Masukin memo ke collection
        val newMemoRef = db.collection("memos").document()

        newMemoRef.set(newMemo)

        newMemoRef.collection("messages")

        //Ambil reference calendar default baru
        val newMemoDocId = newMemoRef.id

        val calendarsArray = arrayListOf(newMemoDocId)

        val userDocumentRef = user?.let { db.collection("users").document(it.uid) }

        //Menambah id Memo baru ke Array di dalam field memos user
        userDocumentRef?.get()?.addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val currentMemosArray = documentSnapshot.get("memos") as? ArrayList<String> ?: ArrayList()
                currentMemosArray.add(newMemoDocId)
                userDocumentRef.update("memos", currentMemosArray)
                    .addOnSuccessListener {
                        // Update successful
                    }
                    .addOnFailureListener { e ->
                        // Handle the failure
                    }
            }
        }
    }

    fun addNewMessages(memoId: String, content: String, date: Date){
        val newMessages = hashMapOf(
            "userId" to user?.uid,
            "content" to content,
            "date" to Date()
        )

        //Masukin ke collection
        val newMessageRef = db.collection("memos").document(memoId).collection("messages")
        newMessageRef.document().set(newMessages)
    }

}