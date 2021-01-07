package com.example.timetable.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.timetable.R
import com.example.timetable.Subject
import com.example.timetable.SubjectRepertory
import com.zhuangfei.timetable.TimetableView

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private var mTimetableView: TimetableView? = null
    private var mySubjects:List<Subject>? = null
    private var alertDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //获取控件
        mTimetableView = root.findViewById(R.id.id_timetableView)
        mTimetableView!!.maxSlideItem(11).itemHeight(65)
        mTimetableView!!.showView()
        requestData()
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    /**
     * 2秒后刷新界面，模拟网络请求
     */
    private fun requestData() {
        alertDialog = AlertDialog.Builder(this.activity)
            .setMessage("模拟请求网络中..")
            .setTitle("Tips").create()
        alertDialog?.show()
        Thread {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            handler.sendEmptyMessage(0x123)
        }.start()
    }
    private var handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (alertDialog != null) alertDialog!!.hide()
            mySubjects = SubjectRepertory.loadDefaultSubjects1()
            Log.d("jj", mySubjects.toString());
            mTimetableView!!.source(mySubjects).showView()
        }
    }
}