package id.ac.umn.kelompokOhana.schedlin.data

class ActivityViewModel {
    fun getActivityList(){
        val calendarInfo = CalendarDataHolder.calendarList
        val memosInfo = MemoDataHolder.memoList


        for (calendar in calendarInfo){
            for(event in calendar.events) {
                val activityInstance = ActivityModel(
                    title = event.title,
                    date = event.dateCreated,
                    type = "Event"
                )
                if(!ActivityDataHolder.activityList.contains(activityInstance)){
                    ActivityDataHolder.activityList.add(activityInstance)
                }
            }

        }

        for (memo in memosInfo){
            val activityInstance = ActivityModel(
                title = memo.name,
                date = memo.date,
                type = "Event"
            )

            if(!ActivityDataHolder.activityList.contains(activityInstance)){
                ActivityDataHolder.activityList.add(activityInstance)
            }
        }
        ActivityDataHolder.activityList.sortByDescending { it.date }

    }
}