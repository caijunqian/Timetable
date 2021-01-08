package com.example.timetable


import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.timetable.PollingUtils.startPollingService
import com.example.timetable.PollingUtils.stopPollingService
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainNavigation : AppCompatActivity() {

    private var navView: BottomNavigationView? = null
    private var navController: NavController? = null
    private var appBarConfiguration:AppBarConfiguration? = null

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