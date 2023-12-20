package id.ac.umn.kelompokOhana.schedlin

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.FirebaseApp
import id.ac.umn.kelompokOhana.schedlin.notif.NOTIFICATION_CHANNEL_ID
import id.ac.umn.kelompokOhana.schedlin.notif.NOTIFICATION_CHANNEL_NAME

class LoginFlow :Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        //Inisialiasasi Channel Notif dan Notification Manager
        val notificationChannel= NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        //Inisialisasi Firebase
        FirebaseApp.initializeApp(this)
    }
}