package id.ac.umn.kelompokOhana.schedlin.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import id.ac.umn.kelompokOhana.schedlin.notif.NotificationManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateMemoViewModel : ViewModel() {
    val db = Firebase.firestore

    //Ngambil current user
    val user = Firebase.auth.currentUser


    //Mengubah date menjadi format yang di inginkan
    fun getDate(input : Date): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(input)
    }
    val date = getDate(Date())

    //Membuat Memo Baru
    fun createNewMemo(name: String, desc: String, calId: String){
        //Membuat list user yang memiliki memo baru tersebut dimulai dari user yang membuat memo
        val userList = arrayListOf(user?.uid)
        //Membuat sebuah instance dari memo baru
        val newMemo = hashMapOf(
            "name" to name,
            "desc" to desc,
            "date" to date,
            "calendarID" to calId,
            "usersID" to userList
        )

        //Masukin memo ke collection
        val newMemoRef = db.collection("memos").document()

        newMemoRef.set(newMemo)

        newMemoRef.collection("messages")

        //Ambil reference calendar default baru
        val newMemoDocId = newMemoRef.id


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

        //Membuat notifikasi bahwa memo baru telah dibuat
        val notificationService = NotificationManager.getNotificationService()
        notificationService.showNotification()
    }


    //Fungsi untuk membuat message baru di dalam sebuah memo
    fun addNewMessages(memoId: String, content: String, date: Date){
        val date2 = getDate(date)
        //Membuat sebuah instance message baru
        val newMessages = hashMapOf(
            "userId" to user?.uid,
            "content" to content,
            "date" to date2
        )

        //Masukin ke collection
        val newMessageRef = db.collection("memos").document(memoId).collection("messages")
        newMessageRef.document().set(newMessages)
    }

    //Function untuk melakukan delete memo
    fun deleteMemo(memoId: String){
        //Mengambil referensi untuk memo yang akan dihapus
        val memoRef = db.collection("memos").document(memoId)
        var userList: ArrayList<String>? = null

        //Mengambil user list di memo kemudian memo di hapus
        memoRef.get().addOnSuccessListener { doc ->
            userList = doc.get("usersId") as? ArrayList<String> ?: ArrayList()
        }
        memoRef.delete()

        //Menghapus id memo tersebut dari daftar memo tiap user
        for (user in userList!!){
            val userRef = db.collection("users").document(user)
            userRef.get().addOnSuccessListener { doc ->
                val currMemos = doc.get("memos") as? ArrayList<String> ?: ArrayList()
                currMemos.remove(memoId)
                userRef?.update("memos", currMemos)
            }
        }
    }

    //Fungsi untuk melakukan update terhadap memo
    fun editMemo(newTitle: String, newDesc: String, memoId: String){
        val memoRef = db.collection("memos").document(memoId)
        memoRef.update("name", newTitle)
        memoRef.update("desc", newDesc)
    }
}