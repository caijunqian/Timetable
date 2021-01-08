package com.example.timetable



import android.annotation.SuppressLint
import android.app.*
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.net.HttpURLConnection
import java.net.URL


class PollingService : Service() {
    private final val ERROR :Int= 2
    private final val JSONSUCCESS :Int= 1
    private val NOTIFICATION_ID: Int = 0
    private val CHANNEL_CODE: String? = "1"
    private var mNotificationChannel: NotificationChannel? = null
    private var mManager: NotificationManager? = null

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
    var count = 0

    internal inner class PollingThread : Thread() {
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun run() {
            //网络请求是否有推送信息
            getMessage()

        }
    }


    private val handler: Handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg?.what){
                JSONSUCCESS->{
                    //analysisJSONData
                    analysisJSONData(msg.obj.toString())
                    //逻辑处理生成推送消息title、bigText
                    if(title != null && bigText !=null){
                        println("Polling...")
                        count++
                        //推送到通知栏
                        createNotification()
                        val triggerAtTime = SystemClock.elapsedRealtime()
                        println("次数为"+count+"时间为"+triggerAtTime)
                        println("New message!")

                    }
                }
                else->
                    return
            }

        }
    }

    private fun analysisJSONData(string: String){
        //下面写：处理返回JSON字符串数据，并且保存到全局变量title、bigTitle
        //do logic

        title = "消息2"
        bigText = "内容"
    }

    //获取推送信息
    private fun getMessage(){

            try {
                val path = "https://api.thecatapi.com/v1/images/search?limit=3&page=100"
                val url: URL = URL(path)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod ="GET"
                connection.connectTimeout=5000
                if(connection.responseCode == 200){
                    var inStream = connection.inputStream
                    var reader = inStream.bufferedReader()
                    val response = StringBuilder()
                    while (true){
                        val line = reader.readLine()?:break
                        response.append(line)
                    }
                    var msg: Message = Message()
                    msg.what = JSONSUCCESS
                    msg.obj = response
                    handler.sendMessage(msg)
                }
                else{
                    var msg :Message = Message()
                    msg.what = ERROR
                    handler.sendMessage(msg)
                }
            }catch (e:Exception){
                e.printStackTrace()
                var msg = Message()
                msg.what = ERROR
                handler.sendMessage(msg)
            }

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