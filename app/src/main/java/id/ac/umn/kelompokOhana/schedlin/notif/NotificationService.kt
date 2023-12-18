package id.ac.umn.kelompokOhana.schedlin.notif

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import id.ac.umn.kelompokOhana.schedlin.MainActivity
import id.ac.umn.kelompokOhana.schedlin.R
import java.util.Random

const val NOTIFICATION_CHANNEL_ID = "1"
const val NOTIFICATION_CHANNEL_NAME = "Activity Notification"
const val NOTIFICATION_ID = 100
const val REQUEST_CODE = 200

class NotificationService(private val context: Context) {

    private val notificationManager=context.getSystemService(NotificationManager::class.java)
    private val myIntent = Intent(context, MainActivity::class.java)
    private val pendingIntent = PendingIntent.getActivity(
        context,
        REQUEST_CODE,
        myIntent,
        PendingIntent.FLAG_IMMUTABLE
    )
    fun showNotification(){
        val notification= NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Ding Ding")
            .setContentText("New Activity Created")
            .setSmallIcon(R.drawable.logo_no_background)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            NOTIFICATION_ID,
            notification
        )
    }
}