package com.example.timetable.ui.notifications

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.timetable.GlobalMsg
import com.example.timetable.R
import com.example.timetable.httpReq.*
import kotlinx.android.synthetic.main.activity_add_notice.*
import kotlinx.android.synthetic.main.activity_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNotice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notice)
    }

    //为Click文本监听，添加日期选择对话框
    @RequiresApi(Build.VERSION_CODES.N)
    fun selectEndTime(view: View){
        val now = Calendar.getInstance()
        val clickText = view as TextView
        val y:Int = now[Calendar.YEAR]
        val m:Int = now[Calendar.MONTH]
        val d:Int = now[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = "${year}-${month + 1}-${dayOfMonth}"
                Log.d("FirstTag", "clickListener: $selectedDate $clickText")
                clickText.text = selectedDate
            }, y, m, d
        )
        datePickerDialog.show()
    }

    fun confirmAdd(view: View) {
        val name = etName.text.toString().trim()
        val detail = etDetail.text.toString().trim()
        val endTime = tvDate.text.toString().trim()
        if(name.isEmpty() || detail.isEmpty() || endTime == "截止日期" )
            Toast.makeText(this, "请先完成输入", Toast.LENGTH_LONG).show()
        else{
            val api = RetrofitUtils.getRetrofit().create(ItemApi::class.java)
            api.insertItem(GlobalMsg.info.userId!!,name,detail,endTime,1)
                .enqueue(object : Callback<ItemBean> {
                    override fun onResponse(call: Call<ItemBean>, response: Response<ItemBean>) {
                        response.let { it ->
                            it.body()?.let {
                                if (it.code == 200)
                                    Toast.makeText(this@AddNotice, "添加成功", Toast.LENGTH_LONG).show()
                                else
                                    Toast.makeText(this@AddNotice, "网络请求失败，请稍后再试", Toast.LENGTH_LONG)
                                        .show()
                            }
                        }
                    }
                    override fun onFailure(call: Call<ItemBean>, t: Throwable) {
                        Toast.makeText(this@AddNotice, "网络请求失败，请稍后再试！", Toast.LENGTH_LONG).show()
                    }
                })
        }
    }

}