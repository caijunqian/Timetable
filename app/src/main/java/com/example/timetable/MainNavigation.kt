package com.example.timetable


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.example.timetable.PollingUtils.startPollingService
import com.example.timetable.work.NotificationWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit
const val CHANNEL_ID="my notification channel Id 1" //CHANNEL_ID不能在普通类里创建
const val NOTIFICATION_ID_OF_ITEM=28 //CHANNEL_ID不能在普通类里创建
class MainNavigation : AppCompatActivity() {

    private var navView: BottomNavigationView? = null
    private var navController: NavController? = null
    private var appBarConfiguration:AppBarConfiguration? = null

    /***Notifications******/
    private val workManager = WorkManager.getInstance(this)
    /**********************/
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        navView= findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration!!)
        navView!!.setupWithNavController(navController!!)


        //Start polling service
        println("Start polling service...")
        startPollingService(this, 1, PollingService::class.java, PollingService.ACTION)

        /***Notifications******/
        makeNoticeWorker()
        /**********************/
    }

    override fun onDestroy() {
        super.onDestroy()
        //Stop polling service
//        println("Stop polling service...")
//        stopPollingService(this, PollingService::class.java, PollingService.ACTION)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return (NavigationUI.navigateUp(navController, appBarConfiguration!!)
                || super.onSupportNavigateUp())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return (item?.let { NavigationUI.onNavDestinationSelected(it, navController) }!!
                || super.onOptionsItemSelected(item))
    }
    private fun makeNoticeWorker(){
        createNotificationChannel() //创建channelId用于worker中创建通知
        val constrain = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setConstraints(constrain)
            .build()
        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(15,TimeUnit.MINUTES)
                .setConstraints(constrain)
                .build()
//        workManager.enqueue(periodicWorkRequest)
        workManager.enqueue(periodicWorkRequest)
//        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this, Observer {
//            if (it.state == WorkInfo.State.SUCCEEDED) {
//                Log.d("workNotice", "onCreate:oneTimeWorkRequest  ${it.outputData.getString("KEY_B")}")
//            }
//        })
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "my channel no.1"
            val descriptionText = "my channel descriptionText no.1"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}