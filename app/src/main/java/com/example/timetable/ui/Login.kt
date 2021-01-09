package com.example.timetable.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.timetable.MainNavigation
import com.example.timetable.R

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun login(view: View) {
        val num = findViewById<EditText>(R.id.editTextNum)
        val pwd = findViewById<EditText>(R.id.editTextPwd)
        Log.d("Login", "login: ${num.text} : ${pwd.text}")
        /*
         根据学号密码访API，然后判断跳转
         */
        //登录后跳转
        startActivity(Intent(this,MainNavigation::class.java))
    }

}