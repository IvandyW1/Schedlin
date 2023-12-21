package id.ac.umn.kelompokOhana.schedlin.data

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import id.ac.umn.kelompokOhana.schedlin.navigation.AppRouter
import id.ac.umn.kelompokOhana.schedlin.navigation.Page
import java.io.ByteArrayOutputStream

class SettingViewModel :ViewModel() {
    //Mengambil reference ke firestore
    val db = Firebase.firestore
    //Mengambil user yang saat ini login
    val user = Firebase.auth.currentUser

    //Fungsi untuk melakukan logout
    fun logout() {

        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "sign out success")
                AppRouter.navigateTo(Page.LoginPage)
            } else {
                Log.d(TAG, "sign out fail")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
        UserDataHolder.currentUser = null
        CalendarDataHolder.calendarList = mutableListOf()
        MemoDataHolder.memoList = mutableListOf()
        ActivityDataHolder.activityList = mutableListOf()
    }

    //Fungsi untuk mendapatkan informasi user yang sedang login
    fun getUserInfo(){
        val user = Firebase.auth.currentUser
        //Mengambil referensi document user yang sedang login
        val userDocRef = user?.let { db.collection("users").document(it.uid) }

        //Menagmbil semua data yang ada
        userDocRef?.get()?.addOnSuccessListener { doc ->
            if(doc.data != null){
                val userDataMap: Map<String, Any> = doc.data!!
                val userModelInstance = UserModel(
                    id = doc.id,
                    name = userDataMap["name"].toString(),
                    email = userDataMap["email"].toString(),
                    profilePict = userDataMap["profilePict"].toString(),
                    calendars = (userDataMap["calendars"] as? List<String>)?.toArrayList() ?: arrayListOf(),
                    memos = (userDataMap["memos"] as? List<String>)?.toArrayList() ?: arrayListOf()
                )

                //Set info yang didapat ke user saat ini
                UserDataHolder.currentUser = userModelInstance
                Log.d("ini", UserDataHolder.currentUser.toString())
            }
        }

        //Dapatkan info calender dari user saat ini
        getCalenderInfo()
    }

    fun <T> List<T>.toArrayList(): ArrayList<T> {
        return ArrayList(this)
    }

    //Fungsi untuk mendapatkan informasi kalender dari user yang sedang login
    fun getCalenderInfo() {
        //Mengambil id semua kalender yang dimiliki user
        val calendarIds: ArrayList<String>? = UserDataHolder.currentUser?.calendars
        Log.d("cek", calendarIds.toString())

        //Lakukan iterasi untuk semua calender yang dimiliki
        if (calendarIds != null) {
            for (ids in calendarIds) {
                val docRef = db.collection("calendars").document(ids)
                docRef.get()
                    .addOnSuccessListener { doc ->
                        if (doc != null) {
                            val calendarDataMap: Map<String, Any> = doc.data!!
                            val eventList = mutableListOf<EventModel>()

                            docRef.collection("events").get()
                                .addOnCompleteListener { doc2 ->
                                    if (doc2.isSuccessful) {
                                        processEvents(doc2.result!!, eventList, calendarDataMap, doc)
                                    } else {
                                        Log.d(TAG, "Error getting events: ", doc2.exception)
                                    }
                                }
                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            }
        }
    }

    //Mengambil info event dan kalender dari user
    private fun processEvents(
        events: QuerySnapshot,
        eventList: MutableList<EventModel>,
        calendarDataMap: Map<String, Any>,
        doc: DocumentSnapshot
    ) {
        //Iterasi untuk mengambil semua event
        for (event in events) {
            val eventDataMap: Map<String, Any> = event.data
            Log.d(TAG, "Event data map: $eventDataMap")
            eventDataMap.let {
                val eventInstance = EventModel(
                    id = event.id,
                    title = eventDataMap["title"].toString(),
                    uid = eventDataMap["userId"].toString(),
                    desc = eventDataMap["desc"].toString(),
                    startTime = eventDataMap["start"].toString(),
                    endTime = eventDataMap["end"].toString(),
                    date = eventDataMap["date"].toString(),
                    dateCreated = eventDataMap["dateCreated"].toString()
                )
                Log.d(TAG, "Event instance: $eventInstance")
                if(!eventList.contains(eventInstance)) {
                    eventList.add(eventInstance)
                }
            }
        }

        //Instance sebuah calender
        Log.d(TAG, "Event array: $eventList")
        val calendarInstance = CalendarModel(
            id = doc.id,
            name = calendarDataMap["name"].toString(),
            usersId = (calendarDataMap["usersId"] as? List<String>)?.toArrayList() ?: arrayListOf(),
            events = eventList
        )

        //Menyimpan ke list calendar saat ini
        Log.d(TAG, "Calendar array: $calendarInstance")
        if(!CalendarDataHolder.calendarList.contains(calendarInstance)) {
            CalendarDataHolder.calendarList.add(calendarInstance)
        }
        Log.d(TAG, "Calendar arrayList: ${CalendarDataHolder.calendarList}")
    }

    //Digunakan untuk mendapatkan informasi memo dari user saat ini
    fun getMemosInfo(){
        //Mengambil id dari semua memo yang dimiliki user
        val memoIds: ArrayList<String>? = UserDataHolder.currentUser?.memos

        //Iterasi untuk mengambil semua memo
        if (memoIds != null) {
            for (ids in memoIds) {
                val docRef = db.collection("memos").document(ids)

                docRef.get()
                    .addOnSuccessListener { doc ->
                        if (doc != null) {
                            val memoDataMap: Map<String, Any> = doc.data!!
                            val messageList = mutableListOf<MessageModel>()

                            docRef.collection("messages").get()
                                .addOnCompleteListener { doc2 ->
                                    if (doc2.isSuccessful) {
                                        processMessages(doc2.result!!, messageList, memoDataMap, doc)
                                    } else {
                                        Log.d(TAG, "Error getting messages: ", doc2.exception)
                                    }
                                }
                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            }
        }
    }

    //Digunakan untuk mengambil semua message dari tiap memo
    private fun processMessages(
        messages: QuerySnapshot,
        messageList: MutableList<MessageModel>,
        memoDataMap: Map<String, Any>,
        doc: DocumentSnapshot
    ) {
        //Iterasi untuk mengambil semua message
        for (message in messages) {
            val messageDataMap: Map<String, Any> = message.data
            Log.d(TAG, "Message data map: $messageDataMap")
            messageDataMap.let {
                //Sebuah instance message
                val eventInstance = MessageModel(
                    id = message.id,
                    content = messageDataMap["content"].toString(),
                    uid = messageDataMap["userId"].toString(),
                    date = messageDataMap["date"].toString()
                )
                //Masukan ke list message
                Log.d(TAG, "Message instance: $eventInstance")
                if(!messageList.contains(eventInstance)) {
                    messageList.add(eventInstance)
                }
            }
        }

        //Sebuah instance memo
        Log.d(TAG, "Message array: $messageList")
        val memoInstance = MemoModel(
            id = doc.id,
            name = memoDataMap["name"].toString(),
            desc = memoDataMap["desc"].toString(),
            date = memoDataMap["date"].toString(),
            usersId = (memoDataMap["usersId"] as? List<String>)?.toArrayList() ?: arrayListOf(),
            cid = memoDataMap["calendarID"].toString(),
            messages = messageList
        )
        Log.d(TAG, "Memos array: $memoInstance")
        //Masukkan ke list memo saat ini
        if(!MemoDataHolder.memoList.contains(memoInstance)) {
            MemoDataHolder.memoList.add(memoInstance)
        }
        Log.d(TAG, "Memos arrayList: ${MemoDataHolder.memoList}")
    }

    //Digunakan untuk melakukan join terhadap calendar dari user lain
    fun joinCalendar(newCalId : String){
        val docRef = user?.let { db.collection("users").document(it.uid) }
        //Menambah id Calendar baru ke Array di dalam field calendars user
        docRef?.get()?.addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val currentCalendarsArray = documentSnapshot.get("calendars") as? ArrayList<String> ?: ArrayList()
                currentCalendarsArray.add(newCalId)
                docRef.update("calendars", currentCalendarsArray)
                    .addOnSuccessListener {
                        // Update successful
                    }
                    .addOnFailureListener { e ->
                        // Handle the failure
                    }
            }
        }
        //Mengambil referensi document calender
        val calRef = db.collection("calendars").document(newCalId)
        //Menambah id user baru ke document calendar
        calRef.get().addOnSuccessListener { doc ->
            val currUsers = doc.get("usersId") as? ArrayList<String> ?: ArrayList()
            user?.let { currUsers.add(it.uid) }
            docRef?.update("usersId", currUsers)
        }
        getCalenderInfo()
    }

    //Function untuk melakukan update informasi user
    fun updateUserInformation(name: String){
        val userRef = user?.let { db.collection("users").document(it.uid) }
        userRef?.update("name", name)
    }

    //Function untuk melakukan update profile picture pengguna
    fun updateAvatarPhoto(uri: Uri){
        val storageRef = Firebase.storage
        val pathReference = storageRef.reference.child("avatar/${user?.uid}")

        pathReference.putFile(uri).addOnSuccessListener {
            user?.let { it1 ->
                db.collection("users").document(it1.uid)
                    .update("profilePict", "avatar/${it1.uid}")
            }
        }
        UserDataHolder.currentUser?.profilePict = "avatar/${user?.uid}"
    }
}