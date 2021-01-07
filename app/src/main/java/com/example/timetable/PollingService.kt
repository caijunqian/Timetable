package com.example.timetable



import android.annotation.SuppressLint
import android.app.*
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class PollingService : Service() {
    private val NOTIFICATION_ID: Int = 0
    private val CHANNEL_CODE: String? = "1"
    private var mNotification: Notification? = null
    private var mNotificationChannel: NotificationChannel? = null
    private var mManager: NotificationManager? = null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        initNotifiManager()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        PollingThread().start()
    }

    //初始化通知栏配置
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initNotifiManager() {
        createNotificationChannel()
//        mManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
//        mNotificationChannel?.let { mManager!!.createNotificationChannel(it) }
    }

    //弹出Notification
    @SuppressLint("WrongConstant")
    private fun showNotification() {
        mNotification!!.`when` = System.currentTimeMillis()
        //Navigator to the new activity when click the notification title
        val intent = Intent(this, MainNavigation::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            Intent.FLAG_ACTIVITY_NEW_TASK
        )
        println("开始提醒")
        mNotification = Notification.Builder(this!!)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("通知")
            .setContentText("您有消息，请及时查看！")
            .setContentIntent(pendingIntent)
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(true)
            .build()
        mManager!!.notify(0, mNotification)
    }

    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     */
    var count = 0

    internal inner class PollingThread : Thread() {
        override fun run() {
            println("Polling...")
            count++
//            showNotification()
            createNotification()
            val triggerAtTime = SystemClock.elapsedRealtime()
            println("时间为"+triggerAtTime)
            println("New message!")

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Service:onDestroy")
    }

    @SuppressLint("WrongConstant")
    fun createNotification() {


        // Create an explicit intent for an Activity in your app
        val intent = Intent(application, MainNavigation::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(application, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK)


        val builder = CHANNEL_CODE?.let {
            NotificationCompat.Builder(application, it)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("消息标题"+count)
                .setContentText("消息内容")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("long notification content")
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }

        with(NotificationManagerCompat.from(application)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, builder!!.build())
        }
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "test_channel"
            val descriptionText = "this is my test channel"

            //other Importance types are
            //IMPORTANCE_HIGH
            //IMPORTANCE_LOW
            //IMPORTANCE_MAX
            //IMPORTANCE_MIN
            val importance = IMPORTANCE_HIGH

            //define your own channel code here i used a predefined constant
            mNotificationChannel = NotificationChannel(CHANNEL_CODE, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            // I am using application class's context here
            mManager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mManager!!.createNotificationChannel(mNotificationChannel!!)
        }
    }

    companion object {
        const val ACTION = "com.example.timetable.PollingService"
    }
}