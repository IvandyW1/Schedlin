package id.ac.umn.kelompokOhana.schedlin.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import id.ac.umn.kelompokOhana.schedlin.navigation.AppRouter
import id.ac.umn.kelompokOhana.schedlin.navigation.Page

class SettingViewModel :ViewModel() {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser

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
    }

    fun getUserInfo(){
        val userDocRef = user?.let { db.collection("users").document(it.uid) }

        userDocRef?.get()?.addOnSuccessListener { doc ->
            if(doc.data != null){

                val userDataMap: Map<String, Any> = doc.data!!
                val userModelInstance = UserModel(
                    id = doc.id,
                    name = userDataMap["name"].toString(),
                    email = userDataMap["email"].toString(),
                    profilePict = userDataMap["profilepict"].toString(),
                    calendars = (userDataMap["calendars"] as? List<String>)?.toArrayList() ?: arrayListOf(),
                    memos = (userDataMap["memos"] as? List<String>)?.toArrayList() ?: arrayListOf()
                )
                UserDataHolder.currentUser = userModelInstance
                Log.d("ini", UserDataHolder.currentUser.toString())
            }
        }
        getCalenderInfo()

    }

    fun <T> List<T>.toArrayList(): ArrayList<T> {
        return ArrayList(this)
    }

    fun getCalenderInfo() {
        val calendarIds: ArrayList<String>? = UserDataHolder.currentUser?.calendars
        Log.d("cek", calendarIds.toString())

        if (calendarIds != null) {
            for (ids in calendarIds) {
                val docRef = db.collection("calendars").document(ids)
                docRef.get()
                    .addOnSuccessListener { doc ->
                        if (doc != null) {
                            Log.d(TAG, "DocumentSnapshot data: ${doc.data}")
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

    private fun processEvents(
        events: QuerySnapshot,
        eventList: MutableList<EventModel>,
        calendarDataMap: Map<String, Any>,
        doc: DocumentSnapshot
    ) {
        for (event in events) {
            val eventDataMap: Map<String, Any> = event.data
            Log.d(TAG, "Event data map: $eventDataMap")
            eventDataMap.let {
                val eventInstance = EventModel(
                    id = event.id,
                    title = eventDataMap["title"].toString(),
                    uid = eventDataMap["userId"].toString(),
                    desc = eventDataMap["desc"].toString(),
                    startTime = eventDataMap["startTime"].toString(),
                    endTime = eventDataMap["endTime"].toString(),
                    date = eventDataMap["date"].toString()
                )
                Log.d(TAG, "Event instance: $eventInstance")
                eventList.add(eventInstance)
            }
        }

        Log.d(TAG, "Event array: $eventList")
        val calendarInstance = CalendarModel(
            id = doc.id,
            name = calendarDataMap["name"].toString(),
            usersId = (calendarDataMap["usersId"] as? List<String>)?.toArrayList() ?: arrayListOf(),
            events = eventList
        )
        Log.d(TAG, "Calendar array: $calendarInstance")
        CalendarDataHolder.calendarList.add(calendarInstance)
        Log.d(TAG, "Calendar arrayList: ${CalendarDataHolder.calendarList}")
    }


    fun getMemosInfo(){
        val memoIds: ArrayList<String>? = UserDataHolder.currentUser?.memos

        if (memoIds != null) {
            for (ids in memoIds) {
                val docRef = db.collection("memos").document(ids)
                docRef.get()
                    .addOnSuccessListener { doc ->
                        if (doc != null) {
                            Log.d(TAG, "DocumentSnapshot data: ${doc.data}")
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

    private fun processMessages(
        messages: QuerySnapshot,
        messageList: MutableList<MessageModel>,
        memoDataMap: Map<String, Any>,
        doc: DocumentSnapshot
    ) {
        for (message in messages) {
            val messageDataMap: Map<String, Any> = message.data
            Log.d(TAG, "Message data map: $messageDataMap")
            messageDataMap.let {
                val eventInstance = MessageModel(
                    id = message.id,
                    content = messageDataMap["content"].toString(),
                    uid = messageDataMap["userId"].toString(),
                    date = messageDataMap["date"].toString()
                )
                Log.d(TAG, "Message instance: $eventInstance")
                messageList.add(eventInstance)
            }
        }

        Log.d(TAG, "Message array: $messageList")
        val memoInstance = MemoModel(
            id = doc.id,
            name = memoDataMap["name"].toString(),
            desc = memoDataMap["desc"].toString(),
            usersId = (memoDataMap["usersId"] as? List<String>)?.toArrayList() ?: arrayListOf(),
            cid = memoDataMap["calendarID"].toString(),
            messages = messageList
        )
        Log.d(TAG, "Memos array: $memoInstance")
        MemoDataHolder.memoList.add(memoInstance)
        Log.d(TAG, "Memos arrayList: ${MemoDataHolder.memoList}")
    }

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
    }
}