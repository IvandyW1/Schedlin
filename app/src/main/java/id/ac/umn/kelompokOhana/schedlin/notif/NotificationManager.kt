package id.ac.umn.kelompokOhana.schedlin.notif

import android.content.Context

object NotificationManager {
    private var notificationService: NotificationService? = null

    fun initialize(context: Context) {
        if (notificationService == null) {
            notificationService = NotificationService(context)
        }
    }

    fun getNotificationService(): NotificationService {
        return notificationService ?: throw IllegalStateException("NotificationService is not initialized.")
    }
}
