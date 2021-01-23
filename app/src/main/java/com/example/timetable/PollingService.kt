package com.example.timetable



import android.annotation.SuppressLint
import android.app.*
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.timetable.httpReq.CourseApi
import com.example.timetable.httpReq.CourseBean
import com.example.timetable.httpReq.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.URL


class PollingService : Service() {
    private final val ERROR :Int= 2
    private final val JSONSUCCESS :Int= 1
    private val NOTIFICATION_ID: Int = 0
    private val CHANNEL_CODE: String? = "1"
    private var mNotificationChannel: NotificationChannel? = null
    private var mManager: NotificationManager? = null

    private var count:Int = 479

    //消息内容
    private var title:String? = "消息";
    private var bigText:String? = "你有作业需要做";


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        //初始化通知栏配置
        createNotificationChannel()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        PollingThread().start()
    }


    override fun onDestroy() {
        super.onDestroy()
        println("Service:onDestroy")
    }

    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     */

    internal inner class PollingThread : Thread() {
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun run() {
            //网络请求是否有推送信息
            getMessage()

        }
    }






    //获取推送信息
    private fun getMessage(){
        val api = RetrofitUtils.getRetrofit().create(CourseApi::class.java)
        api.getCourseOfNextDay(GlobalMsg.info.userId!!)
            .enqueue(object : Callback<CourseBean> {
                override fun onResponse(call: Call<CourseBean>, response: Response<CourseBean>) {
                    response.let { it ->
                        it.body()?.let {
                            if (it.code == 200) {
                                Log.d("jj",it.toString())
                                title = "课程提醒"
                                bigText = it.data.toString()
                                count++
                                println("轮询次数为${count}")
                                //实际每隔一分钟轮询一次，程序已每隔30次即30分钟推送
                                if(count == 480) {
                                    //推送到通知栏
                                    createNotification()
                                    count = 0
                                }
                            }else if(it.code == 204){
                                title = "课程提醒"
                                bigText = "明天没有课程"
                                count++
                                println("轮询次数为${count}")
                                //实际每隔一分钟轮询一次，程序已每隔30次即30分钟推送
                                if(count == 480){
                                    //推送到通知栏
                                    createNotification()
                                    count=0
                                }
                            }
                            else {
                                return
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<CourseBean>, t: Throwable) {
                    return
                }
            })
//            try {
//                val path = "http://8.129.29.84:8080/course/getCourseOfNextDay/1"
//                val url: URL = URL(path)
//                val connection = url.openConnection() as HttpURLConnection
//                connection.requestMethod ="GET"
//                connection.connectTimeout=5000
//                if(connection.responseCode == 200){
//                    var inStream = connection.inputStream
//                    var reader = inStream.bufferedReader()
//                    val response = StringBuilder()
//                    while (true){
//                        val line = reader.readLine()?:break
//                        response.append(line)
//                    }
//                    var msg: Message = Message()
//                    msg.what = JSONSUCCESS
//                    msg.obj = response
//                    handler.sendMessage(msg)
//                }
//                else{
//                    var msg :Message = Message()
//                    msg.what = ERROR
//                    handler.sendMessage(msg)
//                }
//            }catch (e:Exception){
//                e.printStackTrace()
//                var msg = Message()
//                msg.what = ERROR
//                handler.sendMessage(msg)
//            }

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
                .setContentTitle(title!!)
                .setContentText(bigText!!)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(bigText!!)
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