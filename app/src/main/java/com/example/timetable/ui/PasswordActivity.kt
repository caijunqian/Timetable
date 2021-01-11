package com.example.timetable.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.timetable.GlobalMsg
import com.example.timetable.MainNavigation
import com.example.timetable.R
import com.example.timetable.httpReq.InfoApi
import com.example.timetable.httpReq.InfoBean
import com.example.timetable.httpReq.RetrofitUtils
import kotlinx.android.synthetic.main.activity_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
    }

    fun confirmUpdate(view: View) {
        val origin = originPwd.text.toString().trim()
        val new = newPwd.text.toString().trim()
        val repeat = repeatPwd.text.toString().trim()
        if(origin.isEmpty() || new.isEmpty() || repeat.isEmpty())
            Toast.makeText(this, "请先完成输入", Toast.LENGTH_LONG).show()
        else if(new != repeat)
            Toast.makeText(this, "两次新密码不相同", Toast.LENGTH_LONG).show()
        else{
            val api = RetrofitUtils.getRetrofit().create(InfoApi::class.java)
            api.updatePwd(GlobalMsg.info.userId.toString(),origin,repeat)
                .enqueue(object : Callback<InfoBean> {
                    override fun onResponse(call: Call<InfoBean>, response: Response<InfoBean>) {
                        response.let { it ->
                            it.body()?.let {
                                if (it.code == 200) {
                                    Toast.makeText(this@PasswordActivity, "修改成功", Toast.LENGTH_LONG).show()
                                } else if (it.code == 400) {
                                    Toast.makeText(this@PasswordActivity, it.msg, Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(this@PasswordActivity, "网络请求失败，请稍后再试", Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<InfoBean>, t: Throwable) {
                        Toast.makeText(this@PasswordActivity, "网络请求失败，请稍后再试！", Toast.LENGTH_LONG).show()
                    }
                })
        }
    }
}