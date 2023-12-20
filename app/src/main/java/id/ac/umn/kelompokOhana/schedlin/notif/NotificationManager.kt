package id.ac.umn.kelompokOhana.schedlin.notif

import android.content.Context

//Digunakan untuk menyimpan notifikasi agar dapat dipanggil dari mana saja
object NotificationManager {
    //Menyimpan service notifikasi
    private var notificationService: NotificationService? = null

    //Inisialisasi notifikasi
    fun initialize(context: Context) {
        if (notificationService == null) {
            notificationService = NotificationService(context)
        }
    }

    //Mengambil service notifikasi
    fun getNotificationService(): NotificationService {
        return notificationService ?: throw IllegalStateException("NotificationService is not initialized.")
    }
}
