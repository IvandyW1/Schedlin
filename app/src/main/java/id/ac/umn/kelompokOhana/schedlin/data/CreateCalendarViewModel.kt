package id.ac.umn.kelompokOhana.schedlin.data

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import id.ac.umn.kelompokOhana.schedlin.notif.NotificationManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateCalendarViewModel {
    private val user = Firebase.auth.currentUser
    private val db = Firebase.firestore

    fun createNewCalendar(calName: String){
        val userList = arrayListOf(user?.uid)
        //Buat calendar baru
        val newCalendar = hashMapOf(
            "usersId" to userList,
            "name" to calName,
        )

        //Masukin ke collection
        val newCalendarDocRef = db.collection("calendars").document()
        newCalendarDocRef.set(newCalendar)

        newCalendarDocRef.collection("events")

        //Ambil reference calendar default baru
        val newCalendarDocId = newCalendarDocRef.id

        val calendarsArray = arrayListOf(newCalendarDocId)

        val userDocumentRef = user?.let { db.collection("users").document(it.uid) }

        //Menambah id Calendar baru ke Array di dalam field calendars user
        userDocumentRef?.get()?.addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val currentCalendarsArray = documentSnapshot.get("calendars") as? ArrayList<String> ?: ArrayList()
                currentCalendarsArray.add(newCalendarDocId)
                userDocumentRef.update("calendars", currentCalendarsArray)
                    .addOnSuccessListener {
                        // Update successful
                    }
                    .addOnFailureListener { e ->
                        // Handle the failure
                    }
            }
        }
    }

    fun getDate(input: Date): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(input)
    }
    fun addNewEvent(calId:String, date: Date, title: String, start: String, end: String, desc: String){
        val date2 = getDate(date)

        val newEvents = hashMapOf(
            "title" to title,
            "userId" to user?.uid,
            "desc" to desc,
            "start" to start,
            "end" to end,
            "date" to date2
        )

        //Masukin ke collection
        val newEventsRef = db.collection("calendars").document(calId).collection("events")
        newEventsRef.document().set(newEvents)

        val notificationService = NotificationManager.getNotificationService()
        notificationService.showNotification()

    }


}