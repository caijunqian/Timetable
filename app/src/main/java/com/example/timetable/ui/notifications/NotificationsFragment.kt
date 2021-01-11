package com.example.timetable.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timetable.GlobalMsg
import com.example.timetable.MainNavigation
import com.example.timetable.R
import com.example.timetable.httpReq.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var adapter:NotificationsAdapter?= NotificationsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
//        val textView: TextView = root.findViewById(R.id.text_notifications)
//        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=adapter
        //先放一个空的adapter，因为网络请求是异步，不然绑定不了adaptor
        adapter?.notifications = mutableListOf()
        adapter?.let { getNotifications(it,0) }

        //为按钮设置监听
        val btnTodo = view.findViewById<Button>(R.id.button2)
        val btnDone = view.findViewById<Button>(R.id.button3)
        btnTodo.setOnClickListener {
            adapter?.let { getNotifications(it,0) }
        }
        btnDone.setOnClickListener {
            adapter?.let { getNotifications(it,1) }
        }
    }
    override fun onStart() {
        super.onStart()
        adapter?.let { getNotifications(it,0) }
    }
    private fun getNotifications(adapter: NotificationsAdapter,isFinished:Int):Unit{
        val api = RetrofitUtils.getRetrofit().create(ItemApi::class.java)
        api.selectItems(GlobalMsg.info.userId!!,isFinished)
            .enqueue(object : Callback<ItemBean> {
                override fun onResponse(call: Call<ItemBean>, response: Response<ItemBean>) {
                    response.let { it ->
                        it.body()?.let {
                            if (it.code == 200) {
                                if (it.data == null || it.data!!.isEmpty())
                                    Toast.makeText(context, "当前无任何通知事件", Toast.LENGTH_LONG).show()
                                else {
                                    Log.d("TAG", "onResponse:${it.data} ")
                                    adapter.notifications = it.data!!
                                    adapter.notifyDataSetChanged()
                                }
                            } else {
                                Toast.makeText(context, "网络请求失败，请稍后再试", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ItemBean>, t: Throwable) {
                    Toast.makeText(context, "网络请求失败，请稍后再试！", Toast.LENGTH_LONG).show()

                }
            })
    }
}