package com.example.timetable.work

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.timetable.*
import com.example.timetable.httpReq.ItemApi
import com.example.timetable.httpReq.ItemBean
import com.example.timetable.httpReq.RetrofitUtils
import com.example.timetable.ui.notifications.NotificationsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG="workNotice"
var cnt:Int=0
class NotificationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        //以下两行是同步操作
        Log.d(TAG, "doWork: ${cnt++}")
        selectDeadlineItems()
        return Result.success()
    }
    private fun selectDeadlineItems():Unit{
        val api = RetrofitUtils.getRetrofit().create(ItemApi::class.java)
        api.selectDeadlineItems(GlobalMsg.info.userId!!)
            .enqueue(object : Callback<ItemBean> {
                @SuppressLint("WrongConstant")
                override fun onResponse(call: Call<ItemBean>, response: Response<ItemBean>) {
                    Log.d(TAG, "onResponse: 222")
                    response.let { it ->
                        it.body()?.let {
                            if (it.code == 200) {
                                if (it.data != null || it.data!!.isNotEmpty()) {
                                    var noticeName = ""
                                    for (i in it.data!!) {
                                        noticeName += " " + i.name
                                    }
                                    Log.d("workNotice", "onResponse: $noticeName")
                                    //todo 推送从服务器获取的通知
                                    val intent = Intent(
                                        applicationContext,
                                        MainNavigation::class.java
                                    ).apply {
                                        flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK
                                    }
                                    val pendingIntent: PendingIntent =
                                        PendingIntent.getActivity(applicationContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK)
                                    val builder =
                                        NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                                            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                                            .setContentTitle("截止提醒")
                                            .setContentText(noticeName)
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                            .setContentIntent(pendingIntent)
                                            .setAutoCancel(true)
                                    with(NotificationManagerCompat.from(applicationContext)) {
                                        // notificationId is a unique int for each notification that you must define
                                        notify(NOTIFICATION_ID_OF_ITEM, builder.build())
                                    }
                                }

                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ItemBean>, t: Throwable) {
                }
            })
    }
}