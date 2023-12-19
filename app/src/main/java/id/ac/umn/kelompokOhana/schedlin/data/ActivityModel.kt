package id.ac.umn.kelompokOhana.schedlin.data

import java.util.Date

data class ActivityModel(
    var title: String,
    var date : String,
    var type: String
)

object ActivityDataHolder {
    var activityList: MutableList<ActivityModel> = mutableListOf()
}
