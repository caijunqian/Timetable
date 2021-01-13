package com.example.timetable


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
        val constrain = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setConstraints(constrain)
//            .setInputData(workDataOf("KEY_A" to "valueA"))
            .build()
        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(15,TimeUnit.MINUTES,5,TimeUnit.MINUTES)
                .build()
        workManager.enqueue(periodicWorkRequest)
        workManager.enqueue(oneTimeWorkRequest)


//        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this, Observer {
//            if (it.state == WorkInfo.State.SUCCEEDED) {
//                Log.d("workNotice", "onCreate:oneTimeWorkRequest  ${it.outputData.getString("KEY_B")}")
//            }
//        })
//        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id).observe(this, Observer {
//            if (it.state == WorkInfo.State.SUCCEEDED) {
//                Log.d("workNotice", "onCreate: periodicWorkRequest ${it.outputData.getString("KEY_B")}")
//            }
//        })
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

}