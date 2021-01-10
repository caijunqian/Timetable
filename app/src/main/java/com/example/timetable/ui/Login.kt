package com.example.timetable.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.timetable.GlobalMsg
import com.example.timetable.MainNavigation
import com.example.timetable.R
import com.example.timetable.httpReq.InfoApi
import com.example.timetable.httpReq.InfoBean
import com.example.timetable.httpReq.RetrofitUtils
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    val TAG:String="Login"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun login(view: View) {
        val num = editTextNum.text.toString().trim()
        val pwd = editTextPwd.text.toString().trim()
        editTextPwd.text
        Log.d("Login", "${num} ${pwd}")
        /*
         根据学号密码访API，然后判断跳转
         */
        if(num.isEmpty() || pwd.isEmpty()){
            Toast.makeText(this, "请先输入账号和密码", Toast.LENGTH_LONG).show()
        }else{
            //登录后跳转
            val api = RetrofitUtils.getRetrofit().create(InfoApi::class.java)
            api.verifyInfo(num, pwd)
                .enqueue(object : Callback<InfoBean> {
                    override fun onResponse(call: Call<InfoBean>, response: Response<InfoBean>) {
                        response.let { it ->
                            it.body()?.let {
                                if (it.code == 200) {
                                    val intent = Intent(this@Login, MainNavigation::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    Log.d(TAG, "onResponse:${it.data}")
                                    GlobalMsg.info= it.data!!   //登录后把登录信息存入全局变量
                                } else if (it.code == 400) {
                                    Toast.makeText(this@Login, "登录密码错误", Toast.LENGTH_LONG).show()

                                } else {
                                    Toast.makeText(this@Login, "网络请求失败，请稍后再试", Toast.LENGTH_LONG)
                                        .show()

                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<InfoBean>, t: Throwable) {
                        Toast.makeText(this@Login, "网络请求失败，请稍后再试！", Toast.LENGTH_LONG).show()

                    }
                })
        }
    }

}