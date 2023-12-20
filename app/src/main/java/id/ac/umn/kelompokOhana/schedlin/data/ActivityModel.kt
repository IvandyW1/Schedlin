package id.ac.umn.kelompokOhana.schedlin.data

import java.util.Date

//Digunakan untuk membuat model dari sebuah Activity
data class ActivityModel(
    var title: String,
    var date : String,
    var type: String
)

//Digunakan untuk menyimpan list Activity dari user saat ini
object ActivityDataHolder {
    var activityList: MutableList<ActivityModel> = mutableListOf()
}
