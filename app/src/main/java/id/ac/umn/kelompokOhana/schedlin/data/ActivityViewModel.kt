package id.ac.umn.kelompokOhana.schedlin.data

class ActivityViewModel {
    //Digunakan untuk mengambil list Activity dari user saat ini
    fun getActivityList(){
        val calendarInfo = CalendarDataHolder.calendarList
        val memosInfo = MemoDataHolder.memoList

        //Mengambil semua event dari semua calendar
        for (calendar in calendarInfo){
            for(event in calendar.events) {
                val activityInstance = ActivityModel(
                    title = event.title,
                    date = event.dateCreated,
                    type = "Event"
                )
                //Masukan ke list acitivity
                if(!ActivityDataHolder.activityList.contains(activityInstance)){
                    ActivityDataHolder.activityList.add(activityInstance)
                }
            }

        }

        //Mengambil semua memo
        for (memo in memosInfo){
            val activityInstance = ActivityModel(
                title = memo.name,
                date = memo.date,
                type = "Memo"
            )
            //Masukan ke list activity
            if(!ActivityDataHolder.activityList.contains(activityInstance)){
                ActivityDataHolder.activityList.add(activityInstance)
            }
        }
        //Di sorting berdasarkan tanggal
        ActivityDataHolder.activityList.sortByDescending { it.date }

    }
}