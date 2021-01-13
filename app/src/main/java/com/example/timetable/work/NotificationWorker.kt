package com.example.timetable.work

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.timetable.GlobalMsg
import com.example.timetable.httpReq.ItemApi
import com.example.timetable.httpReq.ItemBean
import com.example.timetable.httpReq.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG="workNotice"
class NotificationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
//        val value = inputData.getString("KEY_A")
        //以下两行是同步操作
        selectDeadlineItems()
        return Result.success() //这里可以传递数据出去
    }
    private fun selectDeadlineItems():Unit{
        val api = RetrofitUtils.getRetrofit().create(ItemApi::class.java)
        api.selectDeadlineItems(GlobalMsg.info.userId!!)
            .enqueue(object : Callback<ItemBean> {
                override fun onResponse(call: Call<ItemBean>, response: Response<ItemBean>) {
                    response.let { it ->
                        it.body()?.let {
                            if (it.code == 200) {
                                if (it.data != null || it.data!!.isNotEmpty()){
                                    Log.d("workNotice", "onResponse:${it.data} ")
                                    //todo 推送从服务器获取的通知
                                }

                            }
                        }
                    }
                }
                override fun onFailure(call: Call<ItemBean>, t: Throwable) {
                }
            })
    }
}